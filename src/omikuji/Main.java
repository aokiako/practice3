package omikuji;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * おみくじMainクラス
 * @author a_aoki
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, ParseException {
		//DB(omikuji)に再度データを登録しない
		String rebase = "0";
		if (args.length == 1) {
			rebase = args[0];
		}

		Connection conn = null;
		//接続用のURL用意
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "a_aoki";
		String password = "aoki2005";

		try {
			//DriverManagerクラスのメソッドでpostgresql接続する
			conn = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//今日の日付の取得
		LocalDate today = LocalDate.now();

		try {
			if (rebase.equals("1")) {

				//csv(運勢)読み込み
				File file = new File("omikujiUnsei.csv");
				FileInputStream input = new FileInputStream(file);
				InputStreamReader stream = new InputStreamReader(input, "Shift-JIS");
				BufferedReader buffer = new BufferedReader(stream);

				String line;

				while ((line = buffer.readLine()) != null) {
					//カンマで分ける
					String[] columns = line.split(",", -1);

					//DB(omikuji)へ書き込み
					String sqlin = "INSERT INTO omikuji VALUES(?,?,?,?,?,?,?,?,?)";
					PreparedStatement preparedStatement = conn.prepareStatement(sqlin);

					try {
						preparedStatement = conn.prepareStatement(sqlin);
						preparedStatement.setInt(1, Integer.parseInt(columns[0]));
						preparedStatement.setInt(2, Integer.parseInt(columns[1]));
						preparedStatement.setString(3, columns[2]);
						preparedStatement.setString(4, columns[3]);
						preparedStatement.setString(5, columns[4]);
						preparedStatement.setDate(6, java.sql.Date.valueOf(today));
						preparedStatement.setString(7, "aoki");
						preparedStatement.setDate(8, java.sql.Date.valueOf(today));
						preparedStatement.setString(9, "aoki");

						preparedStatement.executeUpdate();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					buffer.close();
					stream.close();
					input.close();
				}
			}

			//入力チェック
			Pattern p = Pattern.compile("^(\\d{4})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$");
			Matcher match = null;
			String strBirthday = null;
			BufferedReader br = null;
			do {
				System.out.println("誕生日を入力してください。");
				//誕生日の入力
				br = new BufferedReader(new InputStreamReader(System.in));
				strBirthday = br.readLine();
				match = p.matcher(strBirthday);
			} while (!match.find());

			//DBからSELECTした情報で一度入力された誕生日か比較する
			String sqlse2 = "SELECT omikuji_id FROM result WHERE fortune_day = current_date AND birthday=?";
			PreparedStatement pstselectresult = conn.prepareStatement(sqlse2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			java.util.Date dateBirthday = sdf.parse(strBirthday);
			//sql.Dateに変換
			Date sqlDateBd = new java.sql.Date(dateBirthday.getTime());
			pstselectresult.setDate(1, sqlDateBd);
			ResultSet rSet3 = pstselectresult.executeQuery();

			// 結果が存在するか確認する
			boolean existFlg = false;
			int omikujicd = 0;
			// もし結果があればおみくじコードは前回結果を利用する
			if (rSet3.next()) {
				omikujicd = rSet3.getInt(1);
				existFlg = true;
			}
			// もし結果が存在しなければランダムを生成しておみくじコードにする
			if (omikujicd == 0) {
				String sqlse = "SELECT COUNT (*) FROM omikuji";
				PreparedStatement pstcount = conn.prepareStatement(sqlse);
				ResultSet rSet2 = pstcount.executeQuery();
				int max = 0;
				while (rSet2.next()) {
					max = rSet2.getInt(1);
				}

				//ランダム(DB)
				Random rand2 = new Random();
				omikujicd = rand2.nextInt(max);//ランダムで取得したものをomikujicdとする
			}

			//表示したい項目を選ぶ
			String sqlse3 = "SELECT unsei.unsei_name,omikuji.negaigoto,omikuji.akinai,omikuji.gakumon\n" +
					"FROM unsei INNER JOIN omikuji ON unsei.unsei_id = omikuji.unsei_id WHERE omikuji_id=?";

			PreparedStatement pstselectomikuji = conn.prepareStatement(sqlse3);
			pstselectomikuji.setInt(1, omikujicd);
			ResultSet rSet4 = pstselectomikuji.executeQuery();

			Omikuji omikuji = null;
			while (rSet4.next()) {
				switch (rSet4.getString(1)) {
				case "大吉":
					omikuji = new Daikichi();
					break;
				case "中吉":
					omikuji = new Chukichi();
					break;
				case "小吉":
					omikuji = new Syokichi();
					break;
				case "末吉":
					omikuji = new Suekichi();
					break;
				case "吉":
					omikuji = new Kichi();
					break;
				case "凶":
					omikuji = new Kyou();
					break;
				}
				omikuji.setUnsei(rSet4.getString(1));
				omikuji.setNegaigoto(rSet4.getString(2));
				omikuji.setAkinai(rSet4.getString(3));
				omikuji.setGakumon(rSet4.getString(4));
			}

			System.out.println(omikuji.disp());

			if (!existFlg) {
				//DBへ結果の書き込み
				String sqlin2 = "INSERT INTO result VALUES(?,?,?,?,?,?,?)";
				PreparedStatement pstinsertresult = conn.prepareStatement(sqlin2);

				try {
					pstinsertresult = conn.prepareStatement(sqlin2);
					pstinsertresult.setDate(1, java.sql.Date.valueOf(today));
					pstinsertresult.setDate(2, sqlDateBd);
					pstinsertresult.setString(3, "aoki");
					pstinsertresult.setDate(4, java.sql.Date.valueOf(today));
					pstinsertresult.setString(5, "aoki");
					pstinsertresult.setDate(6, java.sql.Date.valueOf(today));
					pstinsertresult.setInt(7, omikujicd);

					pstinsertresult.executeUpdate();

				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}
		} catch (FileNotFoundException fne) {
			System.out.println("ファイルが見つかりません");
			fne.printStackTrace();
		} catch (UnsupportedEncodingException use) {
			System.out.println("指定されたエンコーディングが誤っています");
			use.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}

}
