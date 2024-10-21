package com.example.demo.etl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ETLComponent {

    @Value("classpath:權益/信用卡權益手冊 202410.pdf")
    private Resource pdfResource;

    @Value("classpath:權益/信用卡簽帳消費.md")
    private Resource markdownResource;

    @Value("classpath:權益/旅遊不便險.md")
    private Resource markdownResource2;

    @Value("classpath:權益/Cathay Bank.pdf")
    private Resource pdfResource2;

    private final TokenTextSplitter TOKEN_TEXT_SPLITTER = new TokenTextSplitter();
    private final VectorStore vectorStore;
    private final ChatModel chatModel;
    private KeywordMetadataEnricher keywordMetadataEnricher;
    private SummaryMetadataEnricher summaryMetadataEnricher;

    @PostConstruct
    public void init() {
        keywordMetadataEnricher = new KeywordMetadataEnricher(chatModel, 10);
        summaryMetadataEnricher = new SummaryMetadataEnricher(chatModel,
                List.of(SummaryMetadataEnricher.SummaryType.PREVIOUS,
                        SummaryMetadataEnricher.SummaryType.NEXT,
                        SummaryMetadataEnricher.SummaryType.CURRENT)
        );
    }

    /**
     * 載入資料
     */
    public void loadDataAsDocument() {
        pdfReader(pdfResource);
        pdfReader(pdfResource2);
        textReader(markdownResource);
        textReader(markdownResource2);
    }

    /**
     * 文字檔案額外使用 SummaryMetadataEnricher
     * @param fileResource
     */
    private void textReader(Resource fileResource) {
        var textReader = new TextReader(fileResource);
        var documents = textReader.get();
        log.info("檔案名稱: {}, 資訊:{}", fileResource.getFilename(), documents);
        vectorStore.accept(
                TOKEN_TEXT_SPLITTER.apply(
                        summaryMetadataEnricher.apply(documents)
                )
        );
    }

    /**
     * PDF檔案額外使用 KeywordMetadataEnricher
     * @param fileResource
     */
    private void pdfReader(Resource fileResource) {
        var config = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder().build())
                .build();
        var pdfReader = new PagePdfDocumentReader(fileResource, config);
        var documents = pdfReader.get();
        log.info("檔案名稱: {}, 資訊:{}", fileResource.getFilename(), documents);
        vectorStore.accept(
                TOKEN_TEXT_SPLITTER.apply(
                        keywordMetadataEnricher.apply(documents)
                )
        );
    }
}
