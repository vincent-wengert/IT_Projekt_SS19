package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * In der Klasse <code>Footer</code> werden mittels HTML-Format die Texte für
 * Credits und das Impressum erstellt. Die Anzeige der Texte erfolgt in einer
 * DialogBox.
 * 
 * @author Vincent Wengert, Jan Duwe
 * @version 1.0
 */

public class Footer extends VerticalPanel {
	
	
	private ImpressumDialogBox impressumBox;
	private CreditsDialogBox creditsBox;
	private Label slogan = new Label("Shopping List Verwaltung");
	private HorizontalPanel buttonsPanel = new HorizontalPanel();
	private Button impressumButton = new Button("| Impressum |");
	private Button creditsButton = new Button("| Credits |");

	public void onLoad() {
		this.setWidth("100%");
		slogan.setStylePrimaryName("slogan");
		impressumButton.setStylePrimaryName("impressum");
		creditsButton.setStylePrimaryName("credits");
		this.add(slogan);
		buttonsPanel.add(impressumButton);
		buttonsPanel.add(creditsButton);
		this.add(buttonsPanel);
		this.setCellHorizontalAlignment(slogan, ALIGN_CENTER);
		this.setCellHorizontalAlignment(buttonsPanel, ALIGN_CENTER);

		/*
		 * Zum Öffnen der DialogBoxen werden der Credits und ImpressumButtons
		 * ClickHandler hinzugefügt.
		 */
		impressumButton.addClickHandler(new ImpressumClickHandler());
		creditsButton.addClickHandler(new CreditsClickHandler());

	}

	
	private class Impressum extends HTML {

		// HTML Text für das Impressum

		/**
		 * Mit der Implementierung der privaten <code>Impressum</code>-Klasse wird
		 * mittels HTML-Format das Impressum des Kontaktverwaltungstools angezeigt.
		 */
		public Impressum() {

			this.setHTML(("<div class='Impressum'>" + "</br>" + "<b>Hochschule der Medien</b>" + "</br>"
					+ "<b>Wirtschaftsinformatik und Digitale Medien</b></br>" + "Nobelstra&#223e 10</br>"
					+ "70569 Stuttgart</br></br>"
					+ "Kontakt</br>Telefon: 0711 8923-3242</br> E-Mail: <A HREF=\"mailto:info-wi7@hdm-stuttgart.de\" target=\"_top\">info-wi7@hdm-stuttgart.de "
					+ "<br><A HREF=\"https://www.hdm-stuttgart.de/"
					+ "impressum\"TARGET=\"_blank\">Impressum der Hochschule</A>" + "</div>"));

		}
	}

	
	/**
	 * Hier wird eine private <code>ImpressumDialogBox</code>-Klasse implementiert,
	 * worin beim Öffnen der DialogBox das Impressum angezeigt wird.
	 */
	private class ImpressumDialogBox extends DialogBox {

		private Button cancelButton = new Button("\u2716");

		private VerticalPanel mp = new VerticalPanel();
		private Impressum impressum = new Impressum();

		public ImpressumDialogBox() {

			this.setGlassEnabled(true);
			cancelButton.setPixelSize(130, 40);
			cancelButton.setStylePrimaryName("cancelButton");
			mp.add(impressum);
			mp.add(cancelButton);
			cancelButton.addClickHandler(new CancleClickHandler());
			mp.setCellHorizontalAlignment(cancelButton, HasHorizontalAlignment.ALIGN_CENTER);
			this.add(mp);
			this.center();
		}

		
		/**
		 * Durch die Implementierung des <code>CancelClickHandler</code> wird beim
		 * draufklicken des Cancel-Buttons die DialogBox geschlossen.
		 */
		private class CancleClickHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				ImpressumDialogBox.this.hide();
			}
		}
	}
	
	
	/**
	 * Mit der Implementierung der privaten <code>Credits</code>-Klasse werden
	 * mittels HTML-Format die Credits des Kontaktverwaltungstools angezeigt.
	 */
	private class Credits extends HTML {

		public Credits() {

			this.setHTML(("<div class='Credits'>"
					+ "<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> "
					+ "is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"

					+ "<b>" + "Plus-Icon:" + "</b></br>"
					+ "<div>Icons made by <a href=\"https://www.flaticon.com/authors/google\" title=\"Google\">Google</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a>"
					+ "is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"

					+ "<b>" + "CreateProperty-Icon:" + "</b></br>"
					+ "<div>Icons made by <a href=\"https://www.flaticon.com/authors/smashicons\" title=\"Smashicons\">Smashicons</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a>"
					+ "is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"

					+ "<b>" + "LogOut-Icon:" + "</b></br>"
					+ "<div>Icons made by <a href=\"https://www.flaticon.com/authors/gregor-cresnar\" title=\"Gregor Cresnar\">Gregor Cresnar</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a>"
					+ " is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"

					+ "<b>" + "Search-Icon:" + "</b></br>"
					+ "<div>Icons made by <a href=\"https://www.flaticon.com/authors/simpleicon\" title=\"SimpleIcon\">SimpleIcon</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a>"
					+ "is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"

					+"<b>Background Photo by:</b></br>"
					+ " <div><a href=\"https://unsplash.com/@laup\" target=\"_blank\" rel=\"noopener\">Paul Volkmer</a> on"
					+"<a href=\"https://unsplash.com/\" target=\"_blank\" rel=\"noopener\">Unsplash</a></div>"

			));
		}
	}

	
	/**
	 * Hier wird eine private CreditsDialogBox-Klasse implementiert, worin beim
	 * Öffnen der DialogBox die Credits angezeigt werden.
	 *
	 */
	private class CreditsDialogBox extends DialogBox {

		private Button cancelButton = new Button("\u2716");

		private VerticalPanel mp = new VerticalPanel();

		private Footer.Credits credits = new Footer.Credits();

		
		/*
		 * In die DialogBox wird ein VerticalPanel und im Panel wiederum werden die
		 * Credits und der Button zum schließen der DialogBox hinzugefügt.
		 */
		public CreditsDialogBox() {
			this.setGlassEnabled(true);
			this.center();
			cancelButton.setPixelSize(130, 40);
			cancelButton.setStylePrimaryName("cancelButton");
			mp.add(credits);
			mp.add(cancelButton);

			cancelButton.addClickHandler(new CancelClickHandler());

			mp.setCellHorizontalAlignment(cancelButton, HasHorizontalAlignment.ALIGN_CENTER);
			this.add(mp);
		}

		/**
		 * Durch die Implementierung des <code>CancelClickHandler</code> wird beim
		 * draufklicken des Cancel-Buttons die DialogBox geschlossen.
		 */
		private class CancelClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				CreditsDialogBox.this.hide();
			}
		}

	}

	
	/**
	 * Mit der Implementierung der privaten
	 * <code>ImpressumClickHandler</code>-Klasse wird eine
	 * <code>ImpressumDialogBox</code>-Instanz erzeugt und das Impressum in einer
	 * DialogBox geöffnet.
	 */
	private class ImpressumClickHandler implements ClickHandler {

		/**
		 * Beim Klicken auf den Impressum-Button wird eine Instanz der
		 * <code>ImpressumDialogBox</code> erzeugt.
		 */

		@Override
		public void onClick(ClickEvent event) {
			impressumBox = new ImpressumDialogBox();

		}
	}

	
	/**
	 * Mit der Implementierung der privaten <code>CreditsClickHandler</code>-Klasse
	 * wird eine <code>CreditsDialogBox</code>-Instanz erzeugt und die Credits in
	 * einer DialogBox geöffnet.
	 */
	private class CreditsClickHandler implements ClickHandler {

		/**
		 * Beim Klicken auf den Credits-Button wird eine Instanz der
		 * <code>CreditsDialogBox</code> erzeugt.
		 */
		@Override
		public void onClick(ClickEvent event) {
			creditsBox = new CreditsDialogBox();
		}
	}

}