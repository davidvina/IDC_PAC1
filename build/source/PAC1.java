import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PAC1 extends PApplet {

/**
 *
 * PAC1
 * Integraci\u00f3 digital de continguts
 * @file PAC.pde
 * @author David Vinagre Cerezo
 * @date Abril 2017
 * @version 1
 */

// IMPORTACI\u00d3 DE LLIBRERIES
    // llibreria per interficie
 // llibreria de so basica
 // llibreria per analisis so, necesaria per utilitzar objectes FFT


// DECLARACI\u00d3 D'OBJECTES I VARIABLES GLOBALS

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


public void setup() {
   // mides document
  fonsAplicacio = loadImage("fondo.png"); // carreguem imatge de fons

  // iniciem objecte text
  // Text("string", posicioX, posicioY)
  // missatge.display() -> mostrar\u00e0 el text
  // missatge.update("string") -> actualitzar\u00e0 text
  missatge = new Text("", 140, 267);

  // inicialitzaem interficie per crear botons
  interficie = new ControlP5(this);

  // button.setPosition(posicioX, posicioY);
  // button.setImages(imatgePerDEfecte, rolloverImage, immatgeApretada);
  // button.updateSize() -> ajusta la mida del bot\u00f3 a la de la imatge
  // button.setId(int) -> associa una id al bot\u00f3 necesari per controlar els events de forma f\u00e0cil
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
  // analisisAudio.display() -> mostrar\u00e0 una gr\u00e0fica FFT
  // missatge.update( reproductorAudio) -> actualitzar\u00e0 l'objecte amb un nou audio
  analisisAudio = new AnalisisAudio(audio, 110, 110, 320);
}

public void draw(){
    background(fonsAplicacio); //coloquem el fons de l'aplicaci\u00f3
    missatge.display(); // mostrem el missatge que indica l'estat de l'aplicacio
                        // que \u00e9s el que s'est\u00e0 reproduint
    analisisAudio.display(); // mostrem la gr\u00e0fica que analitza el audio reproduit

    comprobaReproduccio();   // comproba si s'esta reproduint l'audio,
                             // si no, mostra el text d'inici "PRESS A BUTTON TO LISTEN SOUND"
}


/*
 * Aquesta funcio s'encarrega d'escoltar tots els esdeveniments que es fan sobre loadPixels()
 * elements de interf\u00edcie de la llibreria controlP5
 */
public void controlEvent(ControlEvent theEvent) {

  // variable on guardem la id del bot\u00f3 sonre el que
  // s'ha produit un event, sobre el que s'ha clicat
  int controlEventId = theEvent.controller().getId();

  // control de les diferents accions a fer
  // segons el bot\u00f3 clicat
    switch(controlEventId){
      case 1:
        // funci\u00f3 que inicializa el so de l'arxiu "Voz1.wav"
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
 * Funci\u00f3 que comproba si s'esta reproduint l'audio,
 * si no mostra el text que comnida a apretar un bot\u00f3
 */
public void comprobaReproduccio(){
  // si el so no s'esta reproduint
  if (!audio.isPlaying()){
    missatge.update("PRESS A BUTTON TO LISTEN SOUND"); // actualitza el missatge
  };
}

/*
 * Funci\u00f3 que prepara l'audio per reproduir
 * @param arxiuSo -> nom de l'arxiu a reproduir
 * @param t -> text a mostrar
 */
public void reiniciaSo(String arxiuSo, String t){
  // si hi ha un audio reproduint
  // l'atura si no es fa aix\u00f3 es solapariene els audios
  if (audio.isPlaying()){
    audio.pause(); //atura audio
  }

  audio = minim.loadFile(arxiuSo); // carreguem l'arxiu al reproductor
  analisisAudio.update(audio);     // actualitzem l'objecte d'analisis amb el nou audio
  missatge.update(t);              // actualitzem missatge
  audio.play();                    // iniciem so
}
/**
 * @file Text.pde
 *
 * @CLASE Text
 * classe que permet mostrar una gr\u00e1fica FFT
 * a m\u00e9s de posicionar-la
 *
 * @PROPIETATS
 * fft {FFT}                  Objecte FFT que permet analitzar so i crear grafiques
 * audioPlayer {AudioPlayer}  reproductor minim
 * posX0 {entero}             distancia X de la bora esquerra a la gr\u00e0fica
 * posX1 {entero}             distancia X de la bora dreta a la gr\u00e0fica
 * posY {entero}              posicion en Y

 *
 * @METODES
 * update(AudioPlayer)  Actualitza el so associat a la grafica
 * display()            Mostra la gr\u00e0fica
 */

class AnalisisAudio{
  // propietats o camps
  FFT fft;
  AudioPlayer audioPlayer;
  int posX0;
  int posX1;
  int posY;

  // constructor
  AnalisisAudio(AudioPlayer aP, int x0, int x1, int y){
    // carreguem el reproductor
    audioPlayer = aP;

    //carreguem posici\u00f3 gr\u00e0
    posX0 = x0;
    posX1 = x1;
    posY = y;

    // un objecte FFT ha de saber com
    // llargs \u00e9s els buffers d'\u00e0udio que s'analitzaran
    // i tamb\u00e9 necessita saber
    // la freq\u00fc\u00e8ncia de mostreig de l'\u00e0udio que est\u00e0 analitzant
    fft = new FFT(aP.bufferSize(), aP.sampleRate());
  }

  //Setter
  public void update(AudioPlayer aP){
      audioPlayer = aP;
  }

  //Getter
  public void display(){
    stroke(0,255,0);
    strokeWeight(2);
    for(int i = 1; i < (width-(posX1+posX0)) - 1; i++){
      // line(x1, y1, x2, y2)
      line((posX0+i), posY + audioPlayer.left.get(i)*50, (posX0+i+1), posY + audioPlayer.left.get(i+1)*50);
      line((posX0+i), (posY+50) + audioPlayer.right.get(i)*50, (posX0+i+1), (posY+50) + audioPlayer.right.get(i+1)*50);
    }
  }

}
/**
 * @file Text.pde
 *
 * @CLASE Text
 * classe que permet colocar un text
 * he decidit crear aquesta classe per tal de alleugerir el codi de l'arxiu princal
 *
 * @PROPIETATS
 * textContent {string} text a mostrar
 * posX {entero}        posicion X del text
 * posY {entero}        posicion Y
 * tColor {color}       color del text
 * tipografia {PFont}   Caracter\u00edstiques tipografiques
 *
 * @METODES
 * update(String t)   Actualitza la propietat textContent
 * display()          Mostra el text
 */
class Text{
  // propietats
  String textContent;
  int posX;
  int posY;
  int tColor = color(0, 0.8f);
  PFont tipografia = createFont("Unibody_8.ttf", 12);

  // constructor
  Text(String t,int px, int py){
      posX = px;
      posY = py;
      textContent = t;
  }

  // Setter
  public void update(String t){
    textContent = t;
  }

  // Getter
  public void display(){
    textFont(tipografia);
    fill(tColor);
    textAlign(LEFT);
    text(textContent, posX, posY);
  }
}
  public void settings() {  size(960,480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PAC1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
