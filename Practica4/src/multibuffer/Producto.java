package multibuffer;

public class Producto {

	public Producto(int prodID, int prodSeq){
		this.prodID = prodID;
		this.prodSeq = prodSeq;
	//	System.out.println("Producto (" + prodID + "," + prodSeq + ") creado");
	}
	
	public void consumir(int consID, int consSeq) {
//		System.out.println("Producto (" + prodID + "," + prodSeq 
//				+ ") consimido por ("  + consID + "," + consSeq + ")");
	}
	
	public String toString() {
		return "(" + prodID + "," + prodSeq + ")";
		
	}
	
	private int prodID;
	private int prodSeq;
}
