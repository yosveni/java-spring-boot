package com.linkapital.core.services.comment.contract;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.comment.contract.to.CreateCommentTO;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.security.contract.to.LightUserTO;
import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.datasource.domain.Role;
import com.linkapital.core.services.security.datasource.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class CommentBinderImpl implements CommentBinder {

    @Override
    public CommentTO bind(Comment source) {
        if ( source == null ) {
            return null;
        }

        CommentTO commentTO = new CommentTO();

        commentTO.setId( source.getId() );
        commentTO.setComment( source.getComment() );
        commentTO.setLearningNumber( source.getLearningNumber() );
        commentTO.setCreated( source.getCreated() );
        commentTO.setLearningSession( source.getLearningSession() );
        commentTO.setCommentArea( source.getCommentArea() );
        commentTO.setUser( userToLightUserTO( source.getUser() ) );
        Set<Long> set = source.getUsersViews();
        if ( set != null ) {
            commentTO.setUsersViews( new ArrayList<Long>( set ) );
        }
        commentTO.setAttachments( directorySetToDirectoryTOList( source.getAttachments() ) );

        return commentTO;
    }

    @Override
    public Comment bind(CreateCommentTO source) {
        if ( source == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setComment( source.getComment() );
        comment.setLearningNumber( source.getLearningNumber() );
        comment.setLearningSession( source.getLearningSession() );
        comment.setCommentArea( source.getCommentArea() );

        return comment;
    }

    protected RoleTO roleToRoleTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleTO roleTO = new RoleTO();

        roleTO.setId( role.getId() );
        roleTO.setName( role.getName() );
        roleTO.setCode( role.getCode() );
        roleTO.setDescription( role.getDescription() );
        roleTO.setAuthority( role.getAuthority() );

        return roleTO;
    }

    protected LightUserTO userToLightUserTO(User user) {
        if ( user == null ) {
            return null;
        }

        LightUserTO lightUserTO = new LightUserTO();

        lightUserTO.setId( user.getId() );
        lightUserTO.setEmail( user.getEmail() );
        lightUserTO.setName( user.getName() );
        lightUserTO.setCodeCountryPhone( user.getCodeCountryPhone() );
        lightUserTO.setPhone( user.getPhone() );
        lightUserTO.setRole( roleToRoleTO( user.getRole() ) );

        return lightUserTO;
    }

    protected DirectoryTO directoryToDirectoryTO(Directory directory) {
        if ( directory == null ) {
            return null;
        }

        DirectoryTO directoryTO = new DirectoryTO();

        directoryTO.setId( directory.getId() );
        directoryTO.setName( directory.getName() );
        directoryTO.setExt( directory.getExt() );
        directoryTO.setUrl( directory.getUrl() );
        directoryTO.setType( directory.getType() );

        return directoryTO;
    }

    protected List<DirectoryTO> directorySetToDirectoryTOList(Set<Directory> set) {
        if ( set == null ) {
            return null;
        }

        List<DirectoryTO> list = new ArrayList<DirectoryTO>( set.size() );
        for ( Directory directory : set ) {
            list.add( directoryToDirectoryTO( directory ) );
        }

        return list;
    }
}
