package com.hainline.secretsanta;

import java.util.Collection;
import java.util.List;

public interface IGenerator {
	List<Assignment> createAssignmentsForPeople(Collection<Person> people);
}
