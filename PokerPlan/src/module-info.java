module PokerPlan {
	requires javafx.controls;
	requires javafx.fxml;
	 requires javafx.graphics;
	
	opens planning to javafx.fxml;
	 exports planning;
}
