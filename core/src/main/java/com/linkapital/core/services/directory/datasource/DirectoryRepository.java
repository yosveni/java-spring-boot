package com.linkapital.core.services.directory.datasource;

import com.linkapital.core.services.directory.datasource.domain.Directory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Default interface repository for {@link DirectoryRepository}
 * Make database operations for {@link Directory} entity
 *
 * @since 0.0.1
 */
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

}
