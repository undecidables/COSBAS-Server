package org.undecidables.biometric.person;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *  @author Vivian Venter
 *
 *  This interface exists for the sake of making the type of database pluggable.
 *  Extra Functions follow the query style of MongoRepositories.
 */
public interface PersonDBAdapter extends CrudRepository<StaffMember, String> {

    StaffMember findByUserID (String PersonID);

    List<StaffMember> findByName (String Name);
    List<StaffMember> findBySurname (String Surname);


  //  StaffMember findById(String id);

}
