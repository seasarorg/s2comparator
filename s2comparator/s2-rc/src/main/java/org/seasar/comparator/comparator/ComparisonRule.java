package org.seasar.comparator.comparator;

import java.util.Comparator;

public class ComparisonRule {
	String propertyName;
	boolean nullsAreHigh = true;
	boolean ascending = true;
	Comparator comparator;
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public boolean isNullsAreHigh() {
		return nullsAreHigh;
	}
	public void setNullsAreHigh(boolean nullsAreHigh) {
		this.nullsAreHigh = nullsAreHigh;
	}
	public boolean isAscending() {
		return ascending;
	}
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	public Comparator getComparator() {
		return comparator;
	}
	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ascending ? 1231 : 1237);
		result = prime * result
				+ ((comparator == null) ? 0 : comparator.hashCode());
		result = prime * result + (nullsAreHigh ? 1231 : 1237);
		result = prime * result
				+ ((propertyName == null) ? 0 : propertyName.hashCode());
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
		final ComparisonRule other = (ComparisonRule) obj;
		if (ascending != other.ascending)
			return false;
		if (comparator == null) {
			if (other.comparator != null)
				return false;
		} else if (!comparator.equals(other.comparator))
			return false;
		if (nullsAreHigh != other.nullsAreHigh)
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		
		return propertyName + (ascending ? " asc" : " desc") 
		+ (ascending ^ nullsAreHigh ? " nulls first" : " nulls last")
		+ " compared by " + comparator;
	}
	
	
}
