package com.hainline.secretsanta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class GeneratorTest extends TestCase {

	public void testWhenTwoPeopleAreListedThenTheyAreBothSantasOfEachOther() {
		Person person1 = new Person("Adam", "Arlington");
		Person person2 = new Person("Askara", "Barnes");

		IGenerator testObject = new Generator();

		List<Assignment> assignments = testObject.createAssignmentsForPeople(Arrays.asList(person1, person2));

		assertEquals(2, assignments.size());
		Assignment assignment1 = assignments.get(0);
		Assignment assignment2 = assignments.get(1);
		assertEquals(person1, assignment1.getGiver());
		assertEquals(person2, assignment1.getRecipient());
		assertEquals(person2, assignment2.getGiver());
		assertEquals(person1, assignment2.getRecipient());
	}

	public void testWhenSeveralPeopleAreMatchedThenEveryoneIsMatched() {
		Person person1 = new Person("Adam", "Arlington");
		Person person2 = new Person("Askara", "Barnes");
		Person person3 = new Person("Aaron", "Chung");

		IGenerator testObject = new Generator();

		List<Person> people = Arrays.asList(person1, person2, person3);
		List<Assignment> assignments = testObject.createAssignmentsForPeople(people);
		List<Person> givers = getGiversFromAssignments(assignments);
		assertEquals(people, givers);
		List<Person> recipients = getRecipientsFromAssignments(assignments);
		assertTrue(recipients.contains(person1));
		assertTrue(recipients.contains(person2));
		assertTrue(recipients.contains(person3));
		assertFalse(someAssignmentContainsTheSamePersonAsGiverAndRecipient(assignments));
	}

	public void testWhenPeopleWithTheSameNameAreAddedThenAssignmentsDoNotContainFamilyMembers() {
		Person person1 = new Person("Adam", "Arlington");
		Person person2 = new Person("Bob", "Arlington");
		Person person3 = new Person("Askara", "Barnes");
		Person person4 = new Person("Billy", "Barnes");
		Person person5 = new Person("Aaron", "Chung");

		IGenerator testObject = new Generator();

		List<Person> people = Arrays.asList(person1, person2, person3, person4, person5);
		List<Assignment> assignments = testObject.createAssignmentsForPeople(people);
		List<Person> givers = getGiversFromAssignments(assignments);
		assertEquals(people, givers);
		List<Person> recipients = getRecipientsFromAssignments(assignments);
		assertTrue(recipients.contains(person1));
		assertTrue(recipients.contains(person2));
		assertTrue(recipients.contains(person3));
		assertTrue(recipients.contains(person4));
		assertTrue(recipients.contains(person5));
		assertFalse(someAssignmentContainsTheSamePersonAsGiverAndRecipient(assignments));
		assertFalse(someAssignmentContainsGiverAndRecipientFromTheSameFamily(assignments));
	}

	public void testWhenOnlyOnePersonExistsThenNilIsReturned() {
		Person person1 = new Person("Adam", "Arlington");

		IGenerator testObject = new Generator();

		List<Person> people = Arrays.asList(person1);
		List<Assignment> assignments = testObject.createAssignmentsForPeople(people);

		assertNull(assignments);
	}

	public void testWhenNoMatchesExistBecauseOfTooManyOfOneFamilyThenNilIsReturned() {
		Person person1 = new Person("Adam", "Arlington");
		Person person2 = new Person("Bob", "Arlington");
		Person person3 = new Person("Charlie", "Arlington");
		Person person4 = new Person("Alice", "Bones");
		Person person5 = new Person("Betty", "Bones");

		IGenerator testObject = new Generator();

		List<Person> people = Arrays.asList(person1, person2, person3, person4, person5);
		List<Assignment> assignments = testObject.createAssignmentsForPeople(people);

		assertNull(assignments);
	}

	public void testAllMatchesCanBeCreated() {
		Person person1 = new Person("Adam", "Arlington");
		Person person2 = new Person("Bob", "Arlington");
		Person person3 = new Person("Askara", "Barnes");
		Person person4 = new Person("Billy", "Barnes");
		Person person5 = new Person("Aaron", "Chung");

		IGenerator testObject = new Generator();

		List<Person> people = Arrays.asList(person1, person2, person3, person4, person5);
		List<Person> expectedRecipients = Arrays.asList(person5, person3, person2, person1, person4);
		boolean found = false;
		int count = 0;
		while (count < 20000 && !found) {
			count++;
			List<Assignment> assignments = testObject.createAssignmentsForPeople(people);
			List<Person> recipients = getRecipientsFromAssignments(assignments);
			found = recipients.equals(expectedRecipients);
		}
		assertTrue(found);
	}

	public void testGiversAreReturnedInAlphabeticalOrder() {
		Person person1 = new Person("Adam", "Arlington");
		Person person2 = new Person("Bob", "Arlington");
		Person person3 = new Person("Askara", "Barnes");
		Person person4 = new Person("Billy", "Barnes");
		Person person5 = new Person("Aaron", "Chung");

		IGenerator testObject = new Generator();

		List<Person> people = Arrays.asList(person3, person2, person4, person1, person5);
		List<Person> expectedGivers = Arrays.asList(person5, person3, person2, person1, person4);
		List<Assignment> assignments = testObject.createAssignmentsForPeople(people);
		List<Person> givers = getGiversFromAssignments(assignments);
		assertEquals(expectedGivers, givers);
	}

	private List<Person> getGiversFromAssignments(List<Assignment> assignments) {
		List<Person> givers = new ArrayList<Person>();
		for (Assignment assignment : assignments) {
			givers.add(assignment.getGiver());
		}
		return givers;
	}

	private List<Person> getRecipientsFromAssignments(List<Assignment> assignments) {
		List<Person> recipients = new ArrayList<Person>();
		for (Assignment assignment : assignments) {
			recipients.add(assignment.getRecipient());
		}
		return recipients;
	}

	private boolean someAssignmentContainsTheSamePersonAsGiverAndRecipient(List<Assignment> assignments) {
		boolean samePerson = false;
		for (Assignment assignment : assignments) {
			samePerson |= assignment.getGiver().equals(assignment.getRecipient());
		}
		return samePerson;
	}

	private boolean someAssignmentContainsGiverAndRecipientFromTheSameFamily(List<Assignment> assignments) {
		boolean sameFamily = false;
		for (Assignment assignment : assignments) {
			sameFamily |= assignment.getGiver().getLastName().equals(assignment.getRecipient().getLastName());
		}
		return sameFamily;
	}

}
