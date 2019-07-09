package omikuji;

/**
 * おみくじ抽象クラス
 * @author a_aoki
 */
abstract class Omikuji implements Fortune {

	/** 運勢文字列 */
	protected String unsei;

	/** 願い事文字列 */
	protected String negaigoto;

	/** 商い文字列 */
	protected String akinai;

	/** 学問文字列 */
	protected String gakumon;

	/**
	 * 運勢を取得
	 * @return 運勢の文字列
	 */
	public String getUnsei() {
		return unsei;
	}

	/**
	 * 運勢を設定
	 */
	public abstract void setUnsei();

	/**
	 * 願い事を取得
	 * @return 願い事の文字列
	 */
	public String getNegaigoto() {
		return negaigoto;
	}

	/**
	 * 願い事を設定
	 * @param negaigoto 願い事の文字列
	 */

	public void setNegaigoto(String negaigoto) {
		this.negaigoto = negaigoto;
	}

	/**
	 * 商いを取得
	 * @return 商いの文字列
	 */
	public String getAkinai() {
		return akinai;
	}

	/**
	 * 商いを設定
	 * @param akinai 商いの文字列
	 */
	public void setAkinai(String akinai) {
		this.akinai = akinai;
	}

	/**
	 * 学問を取得
	 * @return 学問の文字列
	 */
	public String getGakumon() {
		return gakumon;
	}

	/**
	 * 学問を設定
	 * @param gakumon 学問の文字列
	 */
	public void setGakumon(String gakumon) {
		this.gakumon = gakumon;
	}

	/**
	 * 運勢の文字表現を返却する
	 * @return 運勢の文字表現
	 */
	public String disp() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(DISP_STR, unsei));
		sb.append("\n");
		sb.append("願い事:" + negaigoto);
		sb.append("\n");
		sb.append("商い:" + akinai);
		sb.append("\n");
		sb.append("学問:" + gakumon);
		return sb.toString();
	}
}
