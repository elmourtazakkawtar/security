

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
public class encodeDecodeService {

	public static final int OFFSET = 64;
    public static final String VERSION = "0.88";
    private String mensagem;
    private String ArqData, ArqEntrada, ArqSaida;
    private DataInputStream in , data;
    private DataOutputStream out;
    private int i, j, tamanho;
    private short tamMensagem, temp;
    private int tamArqData, tempInt, tamVetor;
    byte by, byt, byb;

    public encodeDecodeService() {
        i = 0;
        j = 0;
        tamVetor = 0;
    }

    public String getMensagem() {
        return mensagem;
    }

    public boolean codificaMensagem(String msg, String inFile, String outFile) {
        mensagem = msg;
        ArqEntrada = inFile;
        ArqSaida = outFile;

        try {
            // abre o arquivo de dados
            in = new DataInputStream(new FileInputStream(ArqEntrada));
            out = new DataOutputStream(new FileOutputStream(ArqSaida));

            for (i = 0; i <= OFFSET; i++)
                out.writeByte( in .readByte());

            tamMensagem = (short) mensagem.length();

            for (i = 14; i >= 0; i -= 2) {
                temp = tamMensagem;
                temp >>= i; // move os bits em pares de 2 até o LSB (bit menos signficante)
                by = (byte) temp; // e guarda eles no vetor
                by &= 0x03;
                // com o tamMensagem sendo do tipo short 16-bit, será
                // armazenado em 8 pares de 2 bits

                // escreve esses bytes no arquivo de saída 
                byt = in .readByte();
                byt &= 0xFC;
                byt |= by;
                out.writeByte(byt);
            }

            // embute a mensagem
            for (i = 0; i < tamMensagem; i++) {
                byt = (byte) mensagem.charAt(i);
                byt &= 0x7F;

                for (j = 6; j >= 0; j -= 2) {
                    by = byt;
                    by >>= j;
                    by &= 0x03;

                    // escreve esses bytes no arquivo de saída 
                    byb = in .readByte();
                    byb &= 0xFC;
                    byb |= by;
                    out.writeByte(byb);
                }
            }

            // escreve os bytes restantes
            while (true) {
                by = in .readByte();
                out.writeByte(by);
            }
        } catch (EOFException e) {} catch (Exception e) {
            mensagem = "Opa!\nErro: " + e.toString();
            return false;
        } finally {
            try { in .close();
                out.close();
            } catch (Exception ex) {
                mensagem = "Opa!\nErro: " + ex.toString();
                return false;
            }
        }

        mensagem = "Codificada com sucesso.";
        return true;
    }


    public String decodificaMensagem(String inFile) {
        ArqEntrada = inFile;
        boolean flag = true;
        char mesg[] = null;
        try { in = new DataInputStream(new FileInputStream(ArqEntrada));

            // pega o tamanho da mensagem de texto
            tamMensagem = 0;
            for (i = 0; i <= OFFSET; i++)
                in .readByte();

            for (i = 14; i >= 0; i -= 2) {
                by = in .readByte(); // lê um byte do arquivo de entrada
                temp = (short) by;
                temp &= 0x0003;
                temp <<= i; // move os bits em pares de dois pra chegar na posiçao mais à direita
                tamMensagem |= temp;
            }

            if (tamMensagem <= 0) {
                mensagem = "Este arquivo não contem mensagem.";
                return ("#FAILED#");
            }

            mesg = new char[tamMensagem]; // cria o vetor de caracteres do tamanho apropriado para a mensagem
            for (i = 0; i < tamMensagem; i++) {
                by = 0;
                for (j = 6; j >= 0; j -= 2) {
                    byt = in .readByte(); // lê um byte do arquivo de entrada
                    byt &= 0x03;
                    byt <<= j;
                    by |= byt;
                }
                mesg[i] = (char)(((char) by) & 0x007F);
            }
        } catch (Exception e) {
            mensagem = "Opa!\n Erro: " + e;
            return ("#FAILED#");
        } finally {
            try { in .close();
            } catch (Exception ex) {
                mensagem = "Opa!\nErro: " + ex;
                return ("#FAILED#");
            }

            mensagem = "Tamanho da mensagem: " + tamMensagem + " bytes";
            String messg = new String(mesg);
            return messg;
        }
    }
    
    public boolean codificaArquivo(String inFile, String datFile, String outFile) {
        ArqEntrada = inFile;
        ArqData = datFile;
        ArqSaida = outFile;

        try { in = new DataInputStream(new FileInputStream(ArqEntrada));
            data = new DataInputStream(new FileInputStream(ArqData));
            out = new DataOutputStream(new FileOutputStream(ArqSaida));


            // escreve os bytes de OFFSET do arquivo de entrada para o arquivo de saída
            for (i = 0; i <= OFFSET; i++) {
                by = in .readByte();
                out.writeByte(by);
            }

            File file = new File(ArqData);
            mensagem = file.getName();
            tamMensagem = (short) mensagem.length();
            tamArqData = (int) file.length();

            // embute o tamanho do nome do arquivo
            // 8 bits do nome de arquivo vai ser guardado em 4 pares de 2 bits
            // move os bits em pares de 2 até a posição menos significativa
            for (i = 6; i >= 0; i -= 2) {
                temp = tamMensagem;
                temp >>= i;
                by = (byte) temp;
                by &= 0x03;

                // escreve esses bytes no arquivo de saída
                byt = in .readByte();
                byt &= 0xFC;
                byt |= by;
                out.writeByte(byt);
            }

            // embute o arquivo
            for (i = 0; i < tamMensagem; i++) {
                by = (byte) mensagem.charAt(i);
                by &= 0x7F;

                for (j = 6; j >= 0; j -= 2) {
                    byt = by;
                    byt >>= j;
                    byt &= 0x03;

                    // escreve esses bytes no arquivo de saída
                    byb = in .readByte();
                    byb &= 0xFC;
                    byb |= byt;
                    out.writeByte(byb);
                }
            }

            // embute o tamanho do arquivo de dados
            // com tamArqData sendo do tipo long 32-bit, será armazenado em 16 pares de 2 bits
            for (i = 30; i >= 0; i -= 2) {
                tempInt = tamArqData;
                tempInt >>= i;
                by = (byte) tempInt;
                by &= 0x03;

                // escreve esses bytes no arquivo de saída
                byt = in .readByte();
                byt &= 0xFC;
                byt |= by;
                out.writeByte(byt);
            }

            // embute no arquivo
            while (true) {
                byt = data.readByte();

                for (j = 6; j >= 0; j -= 2) {
                    by = byt;
                    by >>= j;
                    by &= 0x03;

                    // escreve esses bytes no arquivo de saída
                    byb = in .readByte();
                    byb &= 0xFC;
                    byb |= by;
                    out.writeByte(byb);
                }
            }
        } catch (EOFException e) {} catch (Exception ex) {
            mensagem = "Opa!\nErro: " + ex.toString();
            return false;
        }

        try {
            // escreve os bytes restantes do arquivo de entrada
            while (true) {
                byt = in .readByte();
                out.writeByte(byt);
            }
        } catch (EOFException e) {} catch (Exception e) {
            mensagem = "Opa!\nErro: " + e.toString();
            return false;
        } finally {
            try { in .close();
                data.close();
                out.close();
            } catch (Exception ex) {
                mensagem = "Opa!\nErro: " + ex.toString();
                return false;
            }
        }

        mensagem = "Arquivo embutido com sucesso em " + new File(ArqSaida).getName();
        return true;
    }

    public String decodificaArquivo(String inFile) {
        char fileName[];
        ArqEntrada = inFile;
        try { in = new DataInputStream(new FileInputStream(ArqEntrada));

            for (i = 0; i <= OFFSET; i++)
                in .readByte();

            tamMensagem = 0;
            // recupera o tamanho do nome do arquivo
            for (i = 6; i >= 0; i -= 2) {
                by = in .readByte();
                by &= 0x03;
                temp = (short) by;
                temp <<= i;
                tamMensagem |= temp;
            }

            // recupera a mensagem
            fileName = new char[tamMensagem];
            for (i = 0; i < tamMensagem; i++) {
                by = 0;
                for (j = 6; j >= 0; j -= 2) {
                    byt = in .readByte();
                    byt &= 0x03;
                    byt <<= j;
                    by |= byt;
                }

                by &= 0x7F;
                fileName[i] = (char) by;
            }

            mensagem = new String(fileName);

            // recupera o tamanho do arquivo de dados
            tamArqData = 0;
            for (i = 30; i >= 0; i -= 2) {
                by = in .readByte();
                tempInt = (int) by;
                tempInt &= 0x00000003;
                tempInt <<= i;
                tamArqData |= tempInt;
            }

            // recupera o arquivo de dados
            // cria um novo arquivo com o nome recuperado
            out = new DataOutputStream(new FileOutputStream(mensagem));

            for (i = 0; i < tamArqData; i++) {
                by = 0;
                for (j = 6; j >= 0; j -= 2) {
                    byt = in .readByte();
                    byt &= 0x03;
                    byt <<= j;
                    by |= byt;
                }

                out.writeByte(by);
            }
        } catch (EOFException e) {} catch (Exception ex) {
            mensagem = "Oops!!\nError: " + ex.toString();
            return "#FAILED#";
        } finally {
            try { in .close();
                out.close();
            } catch (Exception ex) {
                mensagem = "Opa!\nErro: " + ex.toString();
                return "#FAILED#";
            }
        }

        mensagem = "Arquivo recuperado com sucesso como " + mensagem;
        return mensagem;
    }
}