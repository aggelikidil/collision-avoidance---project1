package com.example.redpanther14.threads;


import android.media.AudioManager;
import android.media.ToneGenerator;
import com.example.redpanther14.data.DataSettings;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



public class MosquitoControllerTerminal implements MqttCallback {
    private String myid;
    private String mqtt_ip;
    private String mqtt_port;

    private String topic = myid;

    public MosquitoControllerTerminal(String myid, String mqtt_ip, String mqtt_port) {
        this.myid = myid;
        this.mqtt_ip = mqtt_ip;
        this.mqtt_port = mqtt_port;
        this.topic = myid;
    }

    /**
            *
            *
            * @param  msg  to minima pou ginetai publish me t apotelesmata
 */
    public void publishToAdministrator(String msg) {
        String content      = msg;
        int qos             = 2;
        String broker       = "tcp://" + mqtt_ip + ":" + mqtt_port;
        String clientId     = myid + "s";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            sampleClient.disconnect();
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    public void subscribe() {
        String broker       = "tcp://" + mqtt_ip + ":" + mqtt_port;
        String clientId     = myid;
        MemoryPersistence persistence = new MemoryPersistence();

        MqttClient sampleClient = null;

        System.out.println("Admin controller has started");

        try {
            // subscribe
            sampleClient = new MqttClient(broker, clientId, persistence);
            sampleClient.setCallback(this);

            System.out.println("Connecting to broker to topic of terminal: " + broker);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            sampleClient.connect(connOpts);
            System.out.println("Connected to broker");

            sampleClient.subscribe("result", 2);

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg" + me.getMessage());
            System.out.println("loc" + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep" + me);
            me.printStackTrace();
        }
    }

    /**
            *
            *H sinartisi kaleitai automata se kathe lipsi minimatos apo kapoio kanali
            * @param  topic  to kanali ao to opoio lifthike to minima
            * @param  message   to minima pou lifthike me morfi : terminal01,proximity,1,8,cm,accelerometer,3,0,0,-9.81,m/s^2,50,60,2010-10-10 10:10:10
 */
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String message_content = new String(message.getPayload());

//
        boolean isAutomatic = DataSettings.automatic;
        boolean userWantsToUseInternet = DataSettings.online;

        if ((isAutomatic) || (userWantsToUseInternet)) {
            if (message_content.equals("BOTH CRASHED")) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 200);
            } else if (message_content.equals(myid)) {
                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE, 200);
                }
            }
        }
    public void connectionLost(Throwable throwable) {
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }

}