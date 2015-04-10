package Spaceinvaders;

public class ResourceGet
{

	public ResourceGet()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static String resourceGet(String path)
	{
		String newPath = path.replaceAll("%20", " ");
		return newPath;
	}

}
