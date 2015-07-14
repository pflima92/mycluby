import java.io.IOException;

import br.com.techfullit.mycluby.common.utils.Base64;

public class Base64Test {

	public static void main(String[] args) {

		Base64 b64 = new Base64();
		try {
			System.out.println(b64.encodeFromFile("C:\\default.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
