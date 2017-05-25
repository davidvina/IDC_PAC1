/**
 * @file Text.pde
 *
 * @CLASE Text
 * classe que permet mostrar una gráfica FFT
 * a més de posicionar-la
 *
 * @PROPIETATS
 * fft {FFT}                  Objecte FFT que permet analitzar so i crear grafiques
 * audioPlayer {AudioPlayer}  reproductor minim
 * posX0 {entero}             distancia X de la bora esquerra a la gràfica
 * posX1 {entero}             distancia X de la bora dreta a la gràfica
 * posY {entero}              posicion en Y

 *
 * @METODES
 * update(AudioPlayer)  Actualitza el so associat a la grafica
 * display()            Mostra la gràfica
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

    //carreguem posició grà
    posX0 = x0;
    posX1 = x1;
    posY = y;

    // un objecte FFT ha de saber com
    // llargs és els buffers d'àudio que s'analitzaran
    // i també necessita saber
    // la freqüència de mostreig de l'àudio que està analitzant
    fft = new FFT(aP.bufferSize(), aP.sampleRate());
  }

  //Setter
  void update(AudioPlayer aP){
      audioPlayer = aP;
  }

  //Getter
  void display(){
    stroke(0,255,0);
    strokeWeight(2);
    for(int i = 1; i < (width-(posX1+posX0)) - 1; i++){
      // line(x1, y1, x2, y2)
      line((posX0+i), posY + audioPlayer.left.get(i)*50, (posX0+i+1), posY + audioPlayer.left.get(i+1)*50);
      line((posX0+i), (posY+50) + audioPlayer.right.get(i)*50, (posX0+i+1), (posY+50) + audioPlayer.right.get(i+1)*50);
    }
  }

}
