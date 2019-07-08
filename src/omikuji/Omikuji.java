package omikuji;

/**
 * おみくじ抽象クラス
 * @author a_aoki
 */
abstract class Omikuji implements Fortune {

	protected String unsei;
	protected String negaigoto;
	protected String akinai;
	protected String gakumon;


	public abstract void setUnsei();
	/**運勢を取得
	 * @return
	 */
	public String getUnsei() {
		return unsei;
	}
	/**運勢を設定
	 * @param unsei
	 */
	public void setUnsei(String unsei) {
		this.unsei = unsei;
	}

	/**願い事を取得
	 * @return
	 */
	public String getNegaigoto() {
		return negaigoto;
	}
	/**願い事を設定
	 * @param negaigoto
	 */

	public void setNegaigoto(String negaigoto) {
		this.negaigoto = negaigoto;
	}

	/**商いを取得
	 * @return
	 */	public String getAkinai() {
		return akinai;
	}
	 /**商いを設定
		 * @param akinai
		 */
	public void setAkinai(String akinai) {
		this.akinai = akinai;
	}

	/**学問を取得
	 * @return
	 */	public String getGakumon() {
		return gakumon;
	}
	 /**学問を設定
		 * @param gakumon
		 */
	public void setGakumon(String gakumon) {
		this.gakumon = gakumon;
	}


	//表示させる
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
