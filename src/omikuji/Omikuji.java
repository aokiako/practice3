package omikuji;

/**
 * おみくじ抽象クラス
 * @author a_aoki
 *
 */
abstract class Omikuji implements Fortune {
	protected String unsei;
	protected String negaigoto;
	protected String akinai;
	protected String gakumon;

	public abstract void setUnsei();

	public String getUnsei() {
		return unsei;
	}

	public void setUnsei(String unsei) {
		this.unsei = unsei;
	}

	public String getNegaigoto() {
		return negaigoto;
	}

	public void setNegaigoto(String negaigoto) {
		this.negaigoto = negaigoto;
	}

	public String getAkinai() {
		return akinai;
	}

	public void setAkinai(String akinai) {
		this.akinai = akinai;
	}

	public String getGakumon() {
		return gakumon;
	}

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
