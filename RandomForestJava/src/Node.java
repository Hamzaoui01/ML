import java.util.HashMap;
import java.util.List;

public class Node {
	String title;
	String Decision;
	HashMap<String, Node> values_childrens;

	public Node(String title) {
		this.title = title;
	}

	public String getDecision(List<String> data, List<String> headers) {
		if (Decision == null) {
			String value = data.get(headers.indexOf(title));
			if (values_childrens.get(value) == null) {
				System.out.println("Values are null " + value + " " + values_childrens.size());
				for (String s : values_childrens.keySet())
					System.out.println(s);
			}
			Node e = values_childrens.get(value);
			return e.getDecision(data, headers);
		} else {
			return Decision;
		}
	}

	void afficher(String prefix) {
		System.out.println(prefix + title);
		if (Decision == null) {
			System.out.println(prefix + "Decision: No Decision");
			for (String s : values_childrens.keySet()) {
				System.out.println(prefix + s);
				values_childrens.get(s).afficher(prefix + "\t");
			}
		} else
			System.out.println(prefix + "Decision: " + Decision);
	}
}
