# pong-online
Multiplayer pong game using sockets connection and java applet/2D graphics

<img src="https://teachingkidstocode.io/wp-content/uploads/2017/08/Pong-1920x1200-31.jpg" width="384" height="240" />


The project breaks down into the following modules:

## Graphics Engine:
The graphics engine is used by game client and responsible for rendering the game component onto the screen, the engine is using java applet or java 2D graphics library.
* - [ ] Research Java 2D graphics library - Simon
* - [ ] Model/Component classes: Ball, Board, Game, Field, Prop, Items - Simon
* - [ ] Time(s/ms)-based frame update instead of performance-based frame update(for client synchronization) - Simon

## Physics Engine:
The Graphics Engine will consult the physics engine for interactions such as edge detection, reflection and vector calculation to properly render the game play. The Physics Engine includes:

* - [ ] General Edge Detection - Simon
* - [ ] Edge Reflection - Simon
* - [ ] Vector calculation for board and ball interaction - Simon

## Match-making:
Both game client & server contains modules designed for match making. The module will be using a long socket connection which will also used for in-game communication once a game session is matched. The server side contains thread safe data structure that stores waiting players and match players based on latency. Once a succesful game session is matched, server will send the game session id(for resume socket connection), scores(starts with 0:0), initial conditions(x, y, dx, dy for ball), and beginning timestamp.

* - [ ] Server-side threadsafe data structure for storing and matching waiting players, include the ability for client to cancel the request while waiting - Zero
* - [ ] New request are dispatched and handled by a single thread in server-side workqueue - Zero
* - [ ] Client-side module for match making, cancelling - Simon
* - [ ] Server-side game session tracking - initial x, y, initial vector generation, score tracking - Zero


## In-game communication:
Both game client & server contains modules for in-game communication. These communication includes:

* - [ ] Rebounce Reports: new bounce vector report from client - Zero
* - [ ] Board Position Reports: current board position from client, necessary for real time opponent board rendering - Zero
* - [ ] Round End Reports: reports that the ball entered current players side and represents that the current player lose the round, send from client - Zero
* - [ ] New Round: signals a new round with initial conditions and scores. Send from server to clients. This also works for resumed sessions when a disconnected player reconnect - Zero


## Game dynamics extension:

If we have time, it would be ideal to add more interesting game play into the vintage game. Possible additional gameplay feature:

* - [ ] In game items: accelerator, decelerator, obstacles, random vector, death trap, dual-ball and size changer. - Simon & Zero


# Match-making communications:

```json
{
  "type": "mm_client_hello"
}
EOT
{
  "type": "mm_server_start",
  "opp_host": "123.456.789.001",
  "opp_port": 8888,
  "session_id": 123456789,
  "iv": {
    "x": 40,
    "y": 120,
    "dx": -4.6,
    "dy": 2.6
  },
  "start": 1543278574,
  "left": 0,
  "right": 0
  "you": "left"
}
EOT
```

# In-game communications:
```json
{
    "type": "ig_client_reflect",
    "v": {
        "x": 40,
        "y": 120,
        "dx": -4.6,
        "dy": 2.6
    },
    "start": 1543998574,
}
EOT
{
    "type": "ig_server_broadcast_reflect",
    "v": {
        "x": 40,
        "y": 120,
        "dx": -4.6,
        "dy": 2.6
    },
    "start": 1543999574,
}
EOT
{
    "type": "ig_client_end_round",
}
EOT
{
    "type": "ig_server_end_round",
}
EOT
.
(after many rounds of start -> reflects -> endround)
.
{
    "type": "ig_server_end_game",
    "left": 14,
    "right": 11
}
EOT
```

# Notes:
#### On synchronizations:
An important design choice we have made for this game is that the ball position calculation/rendering will not be done on server and broadcase information to all client. Instead, game server is only responsible for match making, rebounce vector broadcast, and opponent board positions broadcast.

The benefit of this design is that it is much more efficient and can makes the rendering of ball much smoother. It is efficient because as long as the state(items locations, ball: x, y, dx, dy) of both client is synchronized, only rebounce vector is needed to render the entire bounce back until next player bounce the ball again. This is because there are no other user-depended tragectory change after the rebounce. This way all rendering will be done simultaneously on both client in real time, increasing the framerate, and reduce large quatity of position broadcast.

However, this requires that the state of both client must be constantly synchronized. To solve the synchronization problem, we added Epoch timestamp on all communications. This allow the opponent, although may be few hundreds milliseconds delayed for receiving the rebounce report, still be able to calculate the real time position based on the given timestamp, current timestamp, and the rebounce vector.
