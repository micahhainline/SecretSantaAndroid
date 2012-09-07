package com.hainline.secretsanta;

public final class Assignment {
	private final Person giver;
	private final Person recipient;

	public Assignment(Person giver, Person recipient) {
		this.giver = giver;
		this.recipient = recipient;
	}

	public Person getGiver() {
		return giver;
	}

	public Person getRecipient() {
		return recipient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((giver == null) ? 0 : giver.hashCode());
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignment other = (Assignment) obj;
		if (giver == null) {
			if (other.giver != null)
				return false;
		} else if (!giver.equals(other.giver))
			return false;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		return true;
	}
}
