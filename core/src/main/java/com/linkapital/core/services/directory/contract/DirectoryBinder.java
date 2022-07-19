package com.linkapital.core.services.directory.contract;

import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.directory.contract.enums.Type;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static org.springframework.util.StringUtils.split;

/**
 * Default binding class for {@link Directory} transformations
 * Class in charge of constructing the necessary resources to upload or retrieve data from file storage
 *
 * @since 0.0.1
 */
@Mapper
public interface DirectoryBinder {

    DirectoryBinder DIRECTORY_BINDER = Mappers.getMapper(DirectoryBinder.class);

    DirectoryTO bind(Directory source);

    List<DirectoryTO> bind(Collection<Directory> source);

    default Directory bind(String url, String fileName, Company company, Type type) {
        var directory = new Directory();

        directory.setUrl(url);
        directory.setExt("pdf");
        directory.setType(type);
        directory.setName(fileName);
        directory.setCompanyJucesp(company);
        company.getJucespDocuments().add(directory);

        return directory;
    }

    default Directory bind(Type type, String fileName, String ext, String url) {
        var splitName = split(fileName, ".");
        var directory = new Directory();

        directory.setUrl(url);
        directory.setExt(ext);
        directory.setType(type);

        if (splitName == null)
            directory.setName("file");
        else
            directory.setName(splitName.length > 0
                    ? splitName[0]
                    : "file");

        return directory;
    }

    default String bindUrl(String id, String ext) {
        return format("%s/%s.%s", id, randomUUID(), ext);
    }

}
