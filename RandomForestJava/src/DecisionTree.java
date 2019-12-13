import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DecisionTree {

	Node parent;
	List<List<String>> data;

	public DecisionTree(List<List<String>> data) {
		this.data = data;
		List<String> headers = data.remove(0);
		parent = generateTree(data, headers, "");
	}

	public String predict(List<String> data, List<String> headers) {
		try {
			String predection = parent.getDecision(data, headers);
			return predection;
		} catch (Exception e) {
			return "Lost";
		}
	}

	private Node generateTree(List<List<String>> dataset, List<String> headers, String prefix) {
		int selectAttributForParentNode = selectAttributForParentNode(dataset, headers);
		Node current = new Node(headers.get(selectAttributForParentNode));
		List<String> headersChild = new ArrayList<>();
		for (int u = 0; u < headers.size(); u++) {
			if (u != selectAttributForParentNode)
				headersChild.add(headers.get(u));
		}
		if (stats2.get(headers.get(selectAttributForParentNode)) != null)
			for (String val : stats2.get(headers.get(selectAttributForParentNode)).keySet()) {
				List<List<String>> dataChilds = new ArrayList<>();
				for (List<String> line : dataset) {
					if (line.get(selectAttributForParentNode).equals(val)) {
						List<String> auxList = new ArrayList<>();
						for (int u = 0; u < line.size(); u++) {
							if (u != selectAttributForParentNode)
								auxList.add(line.get(u));
						}
						dataChilds.add(auxList);
					}
				}
				if (nb_class(dataChilds).size() == 1) {
					String decision = dataChilds.get(0).get(dataChilds.get(0).size() - 1);
					if (current.values_childrens == null)
						current.values_childrens = new HashMap<>();
					Node d = new Node("");
					d.Decision = decision;
					current.values_childrens.put(val, d);
				} else {
					if (current.values_childrens == null)
						current.values_childrens = new HashMap<>();
					current.values_childrens.put(val, generateTree(dataChilds, headersChild, prefix + "\t"));
				}

			}
		return current;
	}

	private int selectAttributForParentNode(List<List<String>> dataSet, List<String> headers) {
		double gains[] = new double[headers.size() - 1];
		double impurityClass = impurityClass(dataSet);
		for (int i = 0; i < headers.size() - 1; i++) {
			gains[i] = calculGain(dataSet, headers, i, impurityClass);
		}
		return maxIndice(gains);
	}

	private double calculGain(List<List<String>> dataSet, List<String> headers, int indexColumn, double impurityClass) {
		// g=i-ires;
		double gain = impurityClass - impurty(dataSet, headers, indexColumn);
		return gain;
	}

	private double impurityClass(List<List<String>> dataSet) {
		HashMap<String, Integer> a = nb_class(dataSet);
		;
		int nb = 0;
		double impurity = 0;
		for (String s : a.keySet())
			nb += a.get(s);
		for (String s : a.keySet()) {
			double proba = a.get(s) / (double) nb;
			impurity -= proba * Math.log(proba);
		}
		return impurity;
	}

	HashMap<String, HashMap<String, HashMap<String, Integer>>> stats2 = new HashMap<>();

	private double impurty(List<List<String>> dataSet, List<String> headers, int indexColumn) {
		HashMap<String, HashMap<String, Integer>> stats = new HashMap<>();
		for (List<String> line : dataSet) {
			if (stats.get(line.get(indexColumn)) == null) {
				stats.put(line.get(indexColumn), new HashMap<>());
			}
			if (line.get(line.size() - 1).contains("oui")) {
				if (stats.get(line.get(indexColumn)).get("oui") == null) {
					stats.get(line.get(indexColumn)).put("oui", 1);
				} else {
					stats.get(line.get(indexColumn)).put("oui", stats.get(line.get(indexColumn)).get("oui") + 1);
				}
			} else {
				if (stats.get(line.get(indexColumn)).get("non") == null) {
					stats.get(line.get(indexColumn)).put("non", 1);
				} else {
					stats.get(line.get(indexColumn)).put("non", stats.get(line.get(indexColumn)).get("non") + 1);
				}
			}
		}

		double impurty = 0;
		for (String s : stats.keySet()) {
			int nbElem = 0;
			stats.get(s);
			for (String s2 : stats.get(s).keySet())
				nbElem += stats.get(s).get(s2);
			for (String s2 : stats.get(s).keySet()) {
				double probability = (stats.get(s).get(s2) / (double) nbElem);
				impurty -= (probability * Math.log(probability));
			}
		}
		stats2.put(headers.get(indexColumn), stats);
		return impurty;
	}

	private int maxIndice(double[] gains) {
		double max = gains[0];
		int index = 0, i = 0;
		for (double d : gains) {
			if (d > max) {
				max = d;
				index = i;
			}
			i++;
		}
		return index;
	}

	HashMap<String, Integer> nb_class(List<List<String>> dataSet) {
		HashMap<String, Integer> classes = new HashMap<>();
		for (List<String> line : dataSet) {
			String classe = line.get(line.size() - 1);
			if (classes.get(classe) == null)
				classes.put(classe, 1);
			else
				classes.put(classe, classes.get(classe) + 1);
		}
		return classes;
	}

}
