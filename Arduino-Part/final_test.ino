/*
  WiFi Web Server

 A simple web server that shows the value of the analog input pins.
 using a WiFi shield.

 This example is written for a network using WPA encryption. For
 WEP or WPA, change the WiFi.begin() call accordingly.

 Circuit:
 * WiFi shield attached
 * Analog inputs attached to pins A0 through A5 (optional)

 created 13 July 2010
 by dlf (Metodo2 srl)
 modified 31 May 2012
 by Tom Igoe

 */

#include <SPI.h>
#include <WiFi101.h>
/*

#include "arduino_secrets.h" 
///////please enter your sensitive data in the Secret tab/arduino_secrets.h
char ssid[] = "Garino";        // your network SSID (name)
char pass[] = "31011994";    // your network password (use for WPA, or use as key for WEP)
int keyIndex = 0;                 // your network key Index number (needed only for WEP)
*/

char ssid[] = "e-MEI2.4G";        // your network SSID (name)
char pass[] = "eUMa-FCEE-mei";    // your network password (use for WPA, or use as key for WEP)
int keyIndex = 0;                 // your network key Index number (needed only for WEP)

int status = WL_IDLE_STATUS;

WiFiServer server(80);
String output = String(100);
String stringTest = String(100);
String stringFinal = String(100);
int posi = 0;
int index1 = 0;
int index2 = 0;

int r = 0;
int g = 0;
int b = 0;

int state = 0;
#define Redpin 6
#define Greenpin 3
#define Bluepin 9
#define Button 2
void setup() {
  pinMode(Redpin,OUTPUT);
  pinMode(Greenpin,OUTPUT);
  pinMode(Bluepin,OUTPUT);
  pinMode(Button, INPUT);
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // check for the presence of the shield:
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue:
    while (true);
  }

  // attempt to connect to WiFi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);

    // wait 10 seconds for connection:
    delay(10000);
  }
  server.begin();
  // you're connected now, so print out the status:
  printWiFiStatus();

}


void loop() {
  // listen for incoming clients
  WiFiClient client = server.available();
  if (client) {
    Serial.println("new client");
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
         if (output.length() <100)
        {
          output += c;
          //Serial.println(output);
        }
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {
          // send a standard http response header
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // the connection will be closed after completion of the response
          //client.println("Refresh: 5");  // refresh the page automatically every 5 sec
          client.println();
          if (output.indexOf("/") > 0)
          {
            posi = output.length();
            index1= output.indexOf("/");
            stringTest = output.substring(index1,posi);
            index2 = stringTest.indexOf(" ");
            stringFinal = stringTest.substring(1, index2);
            Serial.println(stringFinal);
            //obtain the values for the RGB codes
            int auxIndex1 = 0;
            int auxIndex2 = 0;
            
            String auxR = "";
            String auxG = "";
            String auxB = "";
            auxIndex1 = stringFinal.indexOf("r");
            auxIndex2 = stringFinal.indexOf("?");
            auxR = stringFinal.substring(auxIndex1, auxIndex2);
            stringFinal = stringFinal.substring(auxIndex2 + 1, stringFinal.length());

            auxIndex1 = stringFinal.indexOf("g");
            auxIndex2 = stringFinal.indexOf("?");
            auxG = stringFinal.substring(auxIndex1, auxIndex2);
            stringFinal = stringFinal.substring(auxIndex2 + 1, stringFinal.length());
            auxB = stringFinal;
            r = auxR.substring(2, auxR.length()).toInt();
            g = auxG.substring(2, auxG.length()).toInt();
            b = auxB.substring(2, auxB.length()).toInt();
            Serial.println(r);
            Serial.println(g);
            Serial.println(b);
            
          }
          output = "";
         stringFinal = "";
         stringTest = "";
         Serial.println(output);
         Serial.println("stop");
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        }
        else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);

    // close the connection:
    client.stop();

    Serial.println("client disconnected");
  }
   
 if (digitalRead(Button) == LOW)
 {

    r = 0;
    g = 0;
    b = 0;

 }
 lights(r,g,b);

}
void lights(int r1, int g1, int b1)
{
  analogWrite(Redpin,~r1);
  analogWrite(Greenpin,~g1);
  analogWrite(Bluepin,~b1);
  
}

void printWiFiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
