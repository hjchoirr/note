package org.choongang.global;

public interface Updatable<T> {
    int update(T form);
}

----------------------------------------------------


package org.choongang.global;

import java.util.List;

public interface Listable<T, R> {
    default List<R> getList(T search) { return null; } ;
    default List<R> getList() { return null; };
}


----------------------------------------------------

package org.choongang.student.services;

import org.choongang.global.Listable;
import org.choongang.global.Service;
import org.choongang.global.Updatable;
import org.choongang.student.entities.Subject;
import org.choongang.student.mapper.SubjectMapper;

import java.util.List;

public class SubjectService implements Service<Subject>, Updatable<Subject>, Listable<Object, Subject> {

    private SubjectMapper mapper;

    public SubjectService(SubjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int update(Subject form) {
        return 0;
    }

    @Override
    public List<Subject> getList(Object search) {
        return Listable.super.getList(search);
    }
}

----------------------------------------------------
package org.choongang.student.services;
import lombok.RequiredArgsConstructor;
import org.choongang.global.Listable;
import org.choongang.global.Service;
import org.choongang.student.entities.Subject;
import org.choongang.student.mapper.SubjectMapper;

import java.util.List;

@RequiredArgsConstructor
public class SubjectServiceList implements Service<List<Subject>>, Listable<Object, Subject> {
    private final SubjectMapper mapper;


    @Override
    public List<Subject> process() {
        System.out.println("**SubjectService-process1()");

        return mapper.getSubject();

    }
}




------------------------------------------------------

Updatable<Subject> service = ....

service.update(...)

Listable<Object, Subject> service = ...

List<Subject> items = service.getList(...)




class CommonServiceLocator implements MemberLocator, StudentLocator {

}



interface MemberLocator {
	default JoinService joinService() {
	
	}
	
	
}