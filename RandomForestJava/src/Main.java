import java.util.ArrayList;


public class Main {
	public static void main(String[] args) {
		RandomForest rm = new RandomForest(20);
		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Pif");
		data.add("pluie");
		headers.add("Temp");
		data.add("frais");
		headers.add("Humind");
		data.add("haute");
		headers.add("Vent");
		data.add("faux");
		rm.predict(data, headers);
	}
}
