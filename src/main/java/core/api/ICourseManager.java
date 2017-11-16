package core.api;

public interface ICourseManager {
	
	/**
	 * Exact same specifications as {@link IAdmin#createClass}:
	 * <ol>
	 * <li> year cannot be in the past </li>
	 * <li> capacity must be greater than 0 </li>
	 * <li> no teacher can teach more than two courses in a year </li>
	 * <li> className/year pair must be unique.</li>
	 * </ol>
	 * It explicitly relies on Admin to implement these corerctly!
	 *
	 * <b>IN ADDITION</b>, imposes two added constraints:
	 * <ol>
	 * <li> year cannot be in the future </li>
	 * <li> capacity must be <u>less than</u> 1000 </li>
	 * </ol>
	 */
    void createClass(String className, int year, String instructorName, int capacity);

    // Getters for testing purposes
    /**
     * @return Whether class {@code className} exists in year {@code year}
     */
    boolean classExists(String className, int year);

    /**
     * @return The name of the instructor for class {@code className} in year {@code year}
     */
    String getClassInstructor(String className, int year);

    /**
     * @return The capacity (maximum number of enrollees) for class {@code className} in year {@code year}
     */
    int getClassCapacity(String className, int year);
}
