package com.ikustov.demo.graphql;

import com.ikustov.demo.model.MissingPage;
import com.ikustov.demo.repository.PageRepository;
import io.leangen.graphql.spqr.spring.autoconfigure.DataLoaderRegistryFactory;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class PageDataLoader implements DataLoaderRegistryFactory {

    @Autowired
    private PageRepository pageRepository = new PageRepository();

    private BatchLoader<Integer, List<MissingPage>> pageDataLoader =
            bookIds -> CompletableFuture.supplyAsync(() -> {
                final List<List<MissingPage>> response = new LinkedList<>();
                final List<MissingPage> result = pageRepository.findByBookIdsIn(bookIds);
                if (!CollectionUtils.isEmpty(result)) {
                    Map<Integer, List<MissingPage>> mapOfMissingPages = result
                            .stream()
                            .collect(Collectors.groupingBy(MissingPage::getBookId));
                    bookIds.stream().forEach(key -> {
                        List<MissingPage> pages = mapOfMissingPages.get(key);
                        response.add(pages);
                    });
                }
                return response;
            });

    @Override
    public DataLoaderRegistry createDataLoaderRegistry() {
        DataLoaderOptions options = DataLoaderOptions.newOptions().setBatchingEnabled(true);
        DataLoader<Integer, List<MissingPage>> missingPageDataLoader = DataLoader.newDataLoader(pageDataLoader, options);

        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register("page", missingPageDataLoader);
        return registry;
    }

}
