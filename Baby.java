package s;

public class Baby {
	String _firstName, _lastName, _id;
	Date _dateOfBirth;
	Weight _birthWeight, _currentWeight;
	final String DEFAULT_ID = "000000000";
	

	public Baby(String fName, String lName, String id, int day, int month, int year, int birthWeightInGrams) {
		this._firstName = fName;
		this._lastName = lName;
		this._id = id.length() == 9 ? id : DEFAULT_ID;
		this._dateOfBirth = new Date(day, month, year);
		this._birthWeight = new Weight(birthWeightInGrams);
		this._currentWeight = new Weight(_birthWeight);
	}

	public Baby(Baby other) {
		this._firstName = other._firstName;
		this._lastName = other._lastName;
		this._id = other._id;
		this._dateOfBirth = new Date(other._dateOfBirth);
		this._birthWeight = new Weight(other._birthWeight);
		this._currentWeight = new Weight(other._currentWeight);
	}

	public String getFirstName() {
		return this._firstName;
	}

	public String getLastName() {
		return this._lastName;
	}

	public String getId() {
		return this._id;
	}

	public Date getDateOfBirth() {
		return new Date(this._dateOfBirth);
	}

	public Weight getBirthWeight() {
		return new Weight(this._birthWeight);
	}

	public Weight getCurrentWeight() {
		return new Weight(this._currentWeight);
	}

	public void setCurrentWeight(Weight weightToSet) {
		this._currentWeight = new Weight(weightToSet);
	}

	public String toString() {
		return "Name: " + this._firstName + " " + this._lastName + "\n" + "Id: " + this._id + "\n" + "Date of Birth: "
				+ this._dateOfBirth + "\n" + "Birth Weight: " + this._birthWeight + "\n" + "Current Weight: "
				+ this._currentWeight + "\n";
	}

	public boolean equals(Baby other) {
		return this._firstName.equals(other._firstName) && this._lastName.equals(other._lastName)
				&& this._dateOfBirth.equals(other._dateOfBirth) && this._id.equals(other._id);
	}

	public boolean areTwins(Baby other) {
		return this._lastName.equals(other._lastName) && !this._firstName.equals(other._firstName)
				&& this._id.equals(other._id) && this._dateOfBirth.difference(other._dateOfBirth) <= 1;
	}

	public boolean heavier(Baby other) {
		return this._currentWeight.heavier(other._currentWeight);
	}

	public void updateCurrentWeight(int grams) {
		this._currentWeight = this._currentWeight.add(grams);
	}

	public boolean older(Baby other) {
		return this._dateOfBirth.before(other._dateOfBirth);
	}
	
	public int isWeightInValidRange(int numOfDays) {

		if(numOfDays <= 365){
			double curWeightGrams = this._currentWeight.getGrams() + this._currentWeight.getKilos() * 1000;
			double birthWeightGrams = this._birthWeight.getGrams() + this._birthWeight.getKilos() * 1000;
			return minimumExpectedWeight(numOfDays, birthWeightGrams) >= curWeightGrams ? 3 : 2;
		}
		return 1;
	}	
	//------------------------------------------private methods---------------------------------------------------------------------------//
	
	//Calculate the minimum expected weight, given the age of the baby and the born weight
	private double minimumExpectedWeight(int daysSinceBirth, double bornWeight) {
		
	final int MONTH_0_TO_2_GAIN_PER_DAY = 30; final int DAYS_BETWEEN_0_TO_2 = 53;
	final int MONTH_2_TO_4_GAIN_PER_DAY = 25; final int DAYS_BETWEEN_2_TO_4 = 60;
	final int MONTH_4_TO_8_GAIN_PER_DAY = 16; final int DAYS_BETWEEN_4_TO_8 = 120;
	final int MONTH_8_TO_12_GAIN_PER_DAY = 8; 
		
		if (daysSinceBirth <= 7)  {
			// First week of birth
			return bornWeight - ( ((bornWeight * 0.1) / 7) * daysSinceBirth) ;
		}
		double weightAfterFirstWeek = bornWeight * 0.9;
		
		if (daysSinceBirth <= 60) { 
			//between days 8-60, everyday +30grams
			return weightAfterFirstWeek + (MONTH_0_TO_2_GAIN_PER_DAY * (daysSinceBirth - 7)); 
		}
		
		double weightAfterTwoMonths = weightAfterFirstWeek + MONTH_0_TO_2_GAIN_PER_DAY * DAYS_BETWEEN_0_TO_2;
		
		if (daysSinceBirth <= 120) {
			//between days 61-120, everyday +25grams
			return  weightAfterTwoMonths + MONTH_2_TO_4_GAIN_PER_DAY * (daysSinceBirth - 60); 
		}
		double weightAfterFourMonths = weightAfterTwoMonths + MONTH_2_TO_4_GAIN_PER_DAY *  DAYS_BETWEEN_2_TO_4;
		
		if (daysSinceBirth <= 240) {
			//between days 121-240, every day +16grams
			return weightAfterFourMonths + MONTH_4_TO_8_GAIN_PER_DAY * (daysSinceBirth - 120);
		} 
		double weightAfterEightMonths = weightAfterFourMonths + MONTH_4_TO_8_GAIN_PER_DAY * DAYS_BETWEEN_4_TO_8;
		else {
			//between days 241-365, every day +8grams
			return weightAfterEightMonths + MONTH_8_TO_12_GAIN_PER_DAY * (daysSinceBirth - 240); 
		}

	}
}
