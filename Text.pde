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
 * tipografia {PFont}   Característiques tipografiques
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
  color tColor = color(0, 0.8);
  PFont tipografia = createFont("Unibody_8.ttf", 12);

  // constructor
  Text(String t,int px, int py){
      posX = px;
      posY = py;
      textContent = t;
  }

  // Setter
  void update(String t){
    textContent = t;
  }

  // Getter
  void display(){
    textFont(tipografia);
    fill(tColor);
    textAlign(LEFT);
    text(textContent, posX, posY);
  }
}
