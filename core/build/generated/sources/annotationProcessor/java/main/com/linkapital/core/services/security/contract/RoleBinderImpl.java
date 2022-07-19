package com.linkapital.core.services.security.contract;

import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.contract.to.create.CreateRoleTO;
import com.linkapital.core.services.security.contract.to.update.UpdateRoleTO;
import com.linkapital.core.services.security.datasource.domain.Role;
import com.linkapital.core.services.security.datasource.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class RoleBinderImpl implements RoleBinder {

    @Override
    public Role bind(CreateRoleTO source) {
        if ( source == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( source.getName() );
        role.setCode( source.getCode() );
        role.setDescription( source.getDescription() );
        role.setAuthority( source.getAuthority() );

        return role;
    }

    @Override
    public Role bind(UpdateRoleTO source) {
        if ( source == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( source.getId() );
        role.setName( source.getName() );
        role.setCode( source.getCode() );
        role.setDescription( source.getDescription() );
        role.setAuthority( source.getAuthority() );

        return role;
    }

    @Override
    public RoleTO bind(Role source) {
        if ( source == null ) {
            return null;
        }

        RoleTO roleTO = new RoleTO();

        roleTO.setId( source.getId() );
        roleTO.setName( source.getName() );
        roleTO.setCode( source.getCode() );
        roleTO.setDescription( source.getDescription() );
        roleTO.setAuthority( source.getAuthority() );

        return roleTO;
    }

    @Override
    public List<RoleTO> bind(List<Role> source) {
        if ( source == null ) {
            return null;
        }

        List<RoleTO> list = new ArrayList<RoleTO>( source.size() );
        for ( Role role : source ) {
            list.add( bind( role ) );
        }

        return list;
    }

    @Override
    public Role set(Role target, Role source) {
        if ( source == null ) {
            return null;
        }

        target.setName( source.getName() );
        target.setCode( source.getCode() );
        target.setDescription( source.getDescription() );
        target.setModified( source.getModified() );
        target.setAuthority( source.getAuthority() );
        if ( target.getUsers() != null ) {
            List<User> list = source.getUsers();
            if ( list != null ) {
                target.getUsers().clear();
                target.getUsers().addAll( list );
            }
            else {
                target.setUsers( null );
            }
        }
        else {
            List<User> list = source.getUsers();
            if ( list != null ) {
                target.setUsers( new ArrayList<User>( list ) );
            }
        }

        return target;
    }
}
