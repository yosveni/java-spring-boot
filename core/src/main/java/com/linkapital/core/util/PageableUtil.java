package com.linkapital.core.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Has the responsibility to perform operations over pageable.
 */
@Getter
@Setter
public class PageableUtil {

    private PageableUtil() {
    }

    /**
     * Gets the pageable by given page index and page size.
     *
     * @param page  {@link Integer} The primitive Integer. Zero-based page index, must not be negative.
     * @param count {@link Integer} The primitive Integer. The page size to be returned, must be greater than 0.
     * @param size  {@link Long} The primitive Long. The page max size to be returned, must be greater than 0.
     * @return {@link PageRequest} The PageRequest object.
     */
    public static PageRequest pageUtil(int page, int count, long size) {
        page = Math.max(page - 1, 0);
        count = count <= size ? Math.max(count, 1) : (int) size;
        return PageRequest.of(page, count);
    }

    /**
     * Gets the pageable with sort params applied.
     *
     * @param page  {@link Integer} The primitive Integer. Zero-based page index, must not be negative.
     * @param count {@link Integer} The primitive Integer. The page size to be returned, must be greater than 0.
     * @param size  {@link Long} The primitive Long. The page max size to be returned, must be greater than 0.
     * @param sort  {@link Sort} The sort option for queries. Must not be null, use Sort.unsorted() instead.
     * @return {@link PageRequest} The PageRequest object.
     */
    public static PageRequest pageUtil(int page, int count, long size, Sort sort) {
        page = Math.max(page - 1, 0);
        count = count <= size ? Math.max(count, 1) : (int) size;

        return PageRequest.of(page, count, sort);
    }

    /**
     * Gets a list from another list, given an initial and a final index,
     * The sublist included in the range is obtained and it is returned.
     *
     * @param elements {@link List<T>} The source list.
     * @param page     {@link Integer} The primitive Integer. Zero-based page index, must not be negative.
     * @param count    {@link Integer} The primitive Integer. The page size to be returned, must be greater than 0.
     * @return {@link List<T>} The sublist included in source list, if the range is equal to or
     * Greater than the source list size, it returns source list.
     */
    public static <T> List<T> paginate(List<T> elements, int page, int count) {
        page = Math.max(page, 1);
        count = Math.max(count, 0);
        var fromPage = (page - 1) * count;

        if (fromPage < elements.size()) {
            var toPage = fromPage + count;

            if (toPage > elements.size() || toPage < fromPage)
                toPage = elements.size();

            return elements.subList(fromPage, toPage);
        }

        return elements;
    }

}