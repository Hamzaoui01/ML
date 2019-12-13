import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RandomForest {
	DecisionTree trees[];
	List<List<String>> data = CSVConverter
			.converte("C:\\Users\\USER-PC\\eclipse-workspace\\Exo1\\RandomForest\\src\\book.csv");
	List<String> headers;
	HashMap<String, Integer> predections = new HashMap<>();

	public RandomForest(int numberTrees) {
		// TODO Auto-generated constructor stub
		headers = data.remove(0);
		trees = new DecisionTree[numberTrees];
		for (int i = 0; i < numberTrees; i++) {
			trees[i] = new DecisionTree(randomData());
		}
	}

	void predict(List<String> dataToPredict, List<String> headersToPredict) {
		for (DecisionTree dt : trees) {
			String predection = dt.predict(dataToPredict, headersToPredict);
			if (predections.get(predection) == null)
				predections.put(predection, 1);
			else
				predections.put(predection, predections.get(predection) + 1);
		}
		System.out.println("Predictions \n---------------------------------");
		int max = 0;
		String maxIndex = "";
		for (String s : predections.keySet()) {
			if (predections.get(s) == null)
				break;
			int nb = predections.get(s);
			System.out.println(s + "\t\t" + nb);
			if (nb > max) {
				max = nb;
				maxIndex = s;
			}
		}
		System.out.println("______________________\nPredection Final :\t" + maxIndex);

	}

	private List<List<String>> randomData() {
		// TODO Auto-generated method stub
		List<List<String>> randomData = new ArrayList<List<String>>();
		randomData.add(headers);
		for (int i = 0; i < data.size(); i++) {
			randomData.add(data.get((int) (Math.random() * (((data.size() - 1) - 0) + 1)) + 0));
		}
		return randomData;
	}
}
