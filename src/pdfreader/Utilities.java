package pdfreader;

public class Utilities {

    public static boolean containUnusualCharacter(String s)
    {
    	s.replaceAll("[().,!?]", "");
    	return s.matches(".*[@#$%fjwz&^].*");
    	
    }
}
