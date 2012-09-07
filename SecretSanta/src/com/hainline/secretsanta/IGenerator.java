package com.hainline.secretsanta;

import java.util.Collection;
import java.util.List;

public interface IGenerator {
	// Assignments should be returned in alphabetical order by giver's name
	List<Assignment> createAssignmentsForPeople(Collection<Person> people);
}
