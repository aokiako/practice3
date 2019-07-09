package omikuji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**占いインターフェース
 * @author a_aoki
 *
 */
public interface Fortune {

	/**
	* @param resourceName 文字コード設定
	* @return エンコーディングした結果
	*/
	//文字コードの設定
	static Properties loadUtf8Properties(String resourceName) {
		Properties result = new Properties();

		try (InputStream is = Fortune.class.getResourceAsStream(resourceName);
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader reader = new BufferedReader(isr)) {

			// Properties#load() で渡す Reader オブジェクトを UTF-8 エンコーディング指定して生成した
			// InputStreamReader オブジェクトにする
			result.load(reader);

		} catch (IOException e) {
			e.printStackTrace();

		}
		return result;

	}
	//プロパティファイルからの読み込み
	Properties prop = loadUtf8Properties("/fortune.properties");

	String DISP_STR = prop.getProperty("disp_str");

	String disp();

}
