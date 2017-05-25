/**
 *
 * PAC1
 * Integració digital de continguts
 * @file PAC.pde
 * @author David Vinagre Cerezo
 * @date Abril 2017
 * @version 1
 */

// IMPORTACIÓ DE LLIBRERIES
import controlP5.*;    // llibreria per interficie
import ddf.minim.*; // llibreria de so basica
import ddf.minim.analysis.*; // llibreria per analisis so, necesaria per utilitzar objectes FFT


// DECLARACIÓ D'OBJECTES I VARIABLES GLOBALS

ControlP5 interficie; // controlP5 object
PImage fonsAplicacio; // imatge de fons

// objecte que mostra el text
// Aquest s'utilitza per simplificar el codi d'aquest arxiu
// tot lo referent a l'estil del text o trobarem al arxiu:
// @file: Text.pde
Text missatge; //

// declare d'objecte minim per utilitzar so
Minim minim;
AudioPlayer audio; // reproductor de so

// objecte que mostra analisis de Fourier
// @file AnalisisAudio.pde
AnalisisAudio analisisAudio;


void setup() {
  size(960,480); // mides document
  fonsAplicacio = loadImage("fondo.png"); // carreguem imatge de fons

  // iniciem objecte text
  // Text("string", posicioX, posicioY)
  // missatge.display() -> mostrarà el text
  // missatge.update("string") -> actualitzarà text
  missatge = new Text("", 140, 267);

  // inicialitzaem interficie per crear botons
  interficie = new ControlP5(this);

  // button.setPosition(posicioX, posicioY);
  // button.setImages(imatgePerDEfecte, rolloverImage, immatgeApretada);
  // button.updateSize() -> ajusta la mida del botó a la de la imatge
  // button.setId(int) -> associa una id al botó necesari per controlar els events de forma fàcil
  interficie.addButton("Boton1")
    .setPosition(124,50)
    .setImages(loadImage("Boton1_1.png"),loadImage("Boton1_2.png"),loadImage("Boton1_3.png"))
    .updateSize()
    .setId(1)
    ;

  interficie.addButton("Boton2")
    .setPosition(313,50)
    .setImages(loadImage("Boton2_1.png"),loadImage("Boton2_2.png"),loadImage("Boton2_3.png"))
    .updateSize()
    .setId(2)
    ;

  interficie.addButton("Boton3")
    .setPosition(502,50)
    .setImages(loadImage("Boton3_1.png"),loadImage("Boton3_2.png"),loadImage("Boton3_3.png"))
    .updateSize()
    .setId(3)
    ;

  interficie.addButton("Boton4")
    .setPosition(691,50)
    .setImages(loadImage("Boton4_1.png"),loadImage("Boton4_2.png"),loadImage("Boton4_3.png"))
    .updateSize()
    .setId(4)
    ;

  // create our Minim object for loading audio
  // inicialitzem l'objecte Minim per poder treballar amb audios
  minim = new Minim(this);

  // inicialitzem el reproductor d'audio i l'associem un arxiu de so
  audio = minim.loadFile("Voz1.wav");

  // inicilitzam l'objecte d'analisis de so
  // AnalisisAudio( reproductorAudio, posicioX_Esqurra, posicioX_Dreta, posiciY)
  // analisisAudio.display() -> mostrarà una gràfica FFT
  // missatge.update( reproductorAudio) -> actualitzarà l'objecte amb un nou audio
  analisisAudio = new AnalisisAudio(audio, 110, 110, 320);
}

void draw(){
    background(fonsAplicacio); //coloquem el fons de l'aplicació
    missatge.display(); // mostrem el missatge que indica l'estat de l'aplicacio
                        // que és el que s'està reproduint
    analisisAudio.display(); // mostrem la gràfica que analitza el audio reproduit

    comprobaReproduccio();   // comproba si s'esta reproduint l'audio,
                             // si no, mostra el text d'inici "PRESS A BUTTON TO LISTEN SOUND"
}


/*
 * Aquesta funcio s'encarrega d'escoltar tots els esdeveniments que es fan sobre loadPixels()
 * elements de interfície de la llibreria controlP5
 */
void controlEvent(ControlEvent theEvent) {

  // variable on guardem la id del botó sonre el que
  // s'ha produit un event, sobre el que s'ha clicat
  int controlEventId = theEvent.controller().getId();

  // control de les diferents accions a fer
  // segons el botó clicat
    switch(controlEventId){
      case 1:
        // funció que inicializa el so de l'arxiu "Voz1.wav"
        // i actualitza el missatge
        reiniciaSo("Voz1.wav", "ORIGINAL SOUND");
        break;

      case 2:
        reiniciaSo("Voz2.wav", "ECO FILTER");
        break;

      case 3:
        reiniciaSo("Voz3.wav", "TONE FILTER");
        break;

      case 4:
        reiniciaSo("Voz4.wav", "VOCODER FILTER");
        break;

    } // end switch
}

/*
 * Funció que comproba si s'esta reproduint l'audio,
 * si no mostra el text que comnida a apretar un botó
 */
void comprobaReproduccio(){
  // si el so no s'esta reproduint
  if (!audio.isPlaying()){
    missatge.update("PRESS A BUTTON TO LISTEN SOUND"); // actualitza el missatge
  };
}

/*
 * Funció que prepara l'audio per reproduir
 * @param arxiuSo -> nom de l'arxiu a reproduir
 * @param t -> text a mostrar
 */
void reiniciaSo(String arxiuSo, String t){
  // si hi ha un audio reproduint
  // l'atura si no es fa aixó es solapariene els audios
  if (audio.isPlaying()){
    audio.pause(); //atura audio
  }

  audio = minim.loadFile(arxiuSo); // carreguem l'arxiu al reproductor
  analisisAudio.update(audio);     // actualitzem l'objecte d'analisis amb el nou audio
  missatge.update(t);              // actualitzem missatge
  audio.play();                    // iniciem so
}
