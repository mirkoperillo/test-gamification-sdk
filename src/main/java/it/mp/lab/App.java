package it.mp.lab;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.smartcommunitylab.ApiClient;
import it.smartcommunitylab.ApiException;
import it.smartcommunitylab.basic.api.PlayerControllerApi;
import it.smartcommunitylab.basic.api.PointConceptControllerApi;
import it.smartcommunitylab.model.PlayerStateDTO;
import it.smartcommunitylab.model.ext.PointConcept;

public class App {

    private static final String PWD = "";
    private static final String USER = "";
    private static final String GAMIFICATION_URL = "";
    private static final String PLAYER = "";
    private static final String GAMEID = "";

    public static void main(String[] args) throws JsonParseException, JsonMappingException,
            IllegalArgumentException, ClassNotFoundException, IOException {
        ApiClient client = new ApiClient(GAMIFICATION_URL);
        client.setUsername(USER);
        client.setPassword(PWD);
        PlayerControllerApi playerApi = new PlayerControllerApi(client);

        try {
            PlayerStateDTO state = playerApi.readStateUsingGET(GAMEID, PLAYER);
            Set<it.smartcommunitylab.model.ext.GameConcept> scores =
                    state.getState().get("PointConcept");

            scores.stream().filter(score -> "green leaves".equals(score.getName())).findFirst()
                    .map(concept -> (PointConcept) concept)
                    .ifPresent(score -> System.out
                            .println(score.getName() + " == " + score.getScore()));

            PointConceptControllerApi pointConceptApi = new PointConceptControllerApi(client);

            List<it.smartcommunitylab.model.ext.PointConcept> points =
                    pointConceptApi.readPointsUsingGET1(GAMEID);
            // System.out.println(points);


            // NotificationControllerApi notificationApi = new NotificationControllerApi(client);
            // notificationApi.readPlayerNotificationUsingGET(GAMEID, PLAYER, -1L, -1L, null, null,
            // "1",
            // "50").stream().filter(not -> not instanceof BadgeNotification)
            // .forEach(not -> System.out.print(not));

            playerApi.readInventoryUsingGET(GAMEID, PLAYER);
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
