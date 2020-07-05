package tcp;


import java.io.File;
import java.util.Scanner;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
/**
 *
 * @author admin_70
 */
public class theRecorder {
    private static final long serialVersionUID = 1L;
    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    
    public static void main(String args[]) {
        new theRecorder();
    }
    
    public theRecorder() {
        System.out.println("y��ʼn����");
        Scanner input = new Scanner(System.in);
        String Sinput = input.next();
        long testtime = System.currentTimeMillis();
        if(Sinput.equals("y")){
            captureAudio();//����¼������
        }
        Scanner input_2 = new Scanner(System.in);
        String Sinput_2 = input_2.next();
        if(Sinput_2.equals("n")){
            targetDataLine.stop();
            targetDataLine.close();
        }
        System.out.println("¼����"+(System.currentTimeMillis()-testtime)/1000+"�룡");
    }
    
    public void captureAudio(){
       try {
		audioFormat = getAudioFormat();//����������� PCM ����͸��������� AudioFormat��
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
                //����ָ����Ϣ���������е���Ϣ������Щ��Ϣ����������Ƶ��ʽ���˹��췽��ͨ����Ӧ�ó�����������������С�
                //lineClass - ����Ϣ�����������������е���
                //format - ����ĸ�ʽ
		targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                //������� DataLine���� info �� DataLine.Info ��ʵ��������ָ��һ����ȫ�޶�����Ƶ��ʽ����
                //��һ�������н��������ص� DataLine ��Ĭ�ϸ�ʽ��
		new CaptureThread().start();
                //�����߳�
	} catch (Exception e){
		e.printStackTrace();
		System.exit(0);
	}
    }
    
    private AudioFormat getAudioFormat() {
		float sampleRate = 8000F;
		// 8000,11025,16000,22050,44100 ������
		int sampleSizeInBits = 16;
		// 8,16 ÿ�������е�λ��
		int channels = 2;
		// 1,2 �ŵ�����������Ϊ 1��������Ϊ 2���ȵȣ�
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false ָʾ���� big-endian ˳������ little-endian ˳��洢��Ƶ���ݡ�
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);//����������� PCM ����͸��������� AudioFormat��
	}
    
    class CaptureThread extends Thread {
		public void run() {
			AudioFileFormat.Type fileType = null;
                        //ָ�����ļ�����
			File audioFile = null;
			//�����ļ����ͺ��ļ���չ�� 
			//����ѡ��ĵ�ѡ��ť��
			fileType = AudioFileFormat.Type.WAVE;
			audioFile = new File("test.wav");
			try {
				targetDataLine.open(audioFormat);
                                //format - ������Ƶ��ʽ
				targetDataLine.start();
                                //����ʼ��Ƶ�����ط�ʱ������ START �¼���
				AudioSystem.write(new AudioInputStream(targetDataLine),fileType, audioFile);
                                //new AudioInputStream(TargetDataLine line):�����ָʾ��Ŀ�������ж�ȡ���ݵ���Ƶ�������������ĸ�ʽ��Ŀ�������еĸ�ʽ��ͬ,line - �������л�����ݵ�Ŀ�������С�
                                //stream - ����Ҫд���ļ�����Ƶ���ݵ���Ƶ������
                                //fileType - Ҫд�����Ƶ�ļ�������
                                //out - Ӧ���ļ�����д�����е��ⲿ�ļ� 
 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

