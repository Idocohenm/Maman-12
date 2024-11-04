package s;

public class Date {
	int _day, _month, _year;
	final int DEFAULT_DAY = 1; final int DEFAULT_MONTH = 1; final int DEFAULT_YEAR = 2024; // default values.
	final int MIN_MONTH = 1; final int MIN_DAY = 1; final int MIN_YEAR = 1000; // min day or month or year.
	final int MAX_MONTH = 12; final int MAX_DAY = 31; final int MAX_YEAR = 9999; // max day or month or year.
	
	

	public Date(int day, int month, int year) {
		if (isValidDate(day, month, year)) {
			this._day = day;
			this._month = month;
			this._year = year;
		} else { //Values are not valid, then set it to default.
			this._day = DEFAULT_DAY;
			this._month = DEFAULT_MONTH;
			this._year = DEFAULT_YEAR;
		}

	}

	public Date() {
		this._day = DEFAULT_DAY;
		this._month = DEFAULT_MONTH;
		this._year = DEFAULT_YEAR;
	}

	public Date(Date other) {
		this._day = other._day;
		this._month = other._day;
		this._year = other._day;
	}

	public int getDay() {
		return this._day;
	}

	public int getMonth() {
		return this._month;
	}

	public int getYear() {
		return this._year;
	}

	public void setDay(int dayToSet) {
		if (isValidDate(dayToSet, this._month, this._year)) {
			this._day = dayToSet;
		}
	}

	public void setMonth(int monthToSet) {
		if (isValidDate(this._day, monthToSet, this._year)) {
			this._month = monthToSet;
		}
	}

	public void setYear(int yearToSet) {
		if (isValidDate(this._day, this._month, yearToSet)) {
			this._year = yearToSet;
		}
	}

	public boolean equals(Date other) { 
		return (calculateDate(this._day, this._month, this._year)) == (calculateDate(other._day, other._month,
				other._year)); // if day number since the beginning of the Christian counting of
		// years is equals then it has to be exactly the same date.
	}

	public boolean before(Date other) {
		return (calculateDate(this._day, this._month, this._year)) < (calculateDate(other._day, other._month,
				other._year));// if day number since the beginning of the Christian counting of
		// years is less, then it has to be earlier.

	}

	public boolean after(Date other) {
		return other.before(this);
		//If "other" is before our object, then our object has to be after "other".
	}

	public int difference(Date other) {
		return Math.abs(calculateDate(this._day, this._month, this._year)
				- calculateDate(other._day, other._month, other._year));
		
	}

	public Date tomorrow() {
		if (isValidDate(this._day + 1, this._month, this._year))
			return new Date(this._day + 1, this._month, this._year); 
		if (isValidDate(1, this._month + 1, this._year)) //Start of a new month.
			return new Date(1, this._month + 1, this._year);
		else
			return new Date(1, 1, this._year + 1); // Start of a new year.
	}

	public String toString() {
		String str = "";
		str += this._day < 10 ? "0" + this._day + "/" : this._day + "/"; //Checks if "day" need extra 0.
		str += this._month < 10 ? "0" + this._month + "/" : this._month + "/";//Checks if "month" need extra 0.
		str += this._year; // adding year finally.
		return str;
	}
//-------------------------------------------------Private Methods-----------------------------------------//
	// computes the day number since the beginning of the Christian counting of
	// years
	private int calculateDate(int day, int month, int year) {
		if (month < 3) {
			year--;
			month = month + 12;
		}
		return 365 * year + year / 4 - year / 100 + year / 400 + ((month + 1) * 306) / 10 + (day - 62);
	}

	// checks if the year is a leap year
	private boolean isLeapYear(int y) {
		return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
	}
// checks if the date is valid
	private boolean isValidDate(int d, int m, int y) {
		final int MAX_28 = 28; final int MAX_29 = 29; // For February.
		final int MAX_30 = 30;  // For any other month.
		
		
		if (d >= MIN_DAY && d <= MAX_DAY && m >= MIN_MONTH && m <= MAX_MONTH && y >= MIN_YEAR && y <= MAX_YEAR) {
			if (m == 4 || m == 6 || m == 9 || m == 11) // In months April, June, September, November max 30 days.
				return d <= MAX_30;
			if (m == 2) {
				if (isLeapYear(y)) {
					return d <= MAX_29; // In February, in a leap year max 29 days.
				} else
					return d <= MAX_28; // In February, max 28 days in common year.
			}
			return true; //date is within defined limits
		}
		return false; // If day or month or year is not within the defined limits (31>day>0,
						// 12>month>0, 9999>year>1000).

	}
}
