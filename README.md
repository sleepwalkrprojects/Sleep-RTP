# SleepRTP
A lightweight random teleport plugin for Minecraft servers with safe location detection.

## Core Features
- Random teleportation with single command
- Configurable teleport radius (min/max distance)
- Intelligent safe location detection
- Avoids lava, water, and unsafe spawns
- Multiple attempt system for reliability
- Permission-based access control
- Customizable configuration

## Installation
1. Download the latest release
2. Place `SleepRTP.jar` in `plugins/` folder
3. Restart server
4. Configure `plugins/SleepRTP/config.yml`

## Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/rtp` | Teleport to random safe location | `sleeprtp.use` |

## Permissions
| Permission | Description | Default |
|------------|-------------|---------|
| `sleeprtp.use` | Use random teleport | `true` |

## Configuration
Settings stored in `plugins/SleepRTP/config.yml`:
```yaml
# Maximum radius for random teleportation (in blocks)
max-radius: 5000

# Minimum radius for random teleportation (in blocks)
min-radius: 500

# Maximum attempts to find a safe location
max-attempts: 10
```

### Configuration Options
- **max-radius**: Furthest distance from origin for teleportation
- **min-radius**: Closest distance from origin for teleportation
- **max-attempts**: Number of tries to find safe location before failing

## How It Works
1. Player executes `/rtp` command
2. Plugin generates random coordinates within configured radius
3. Checks for safe landing spot (solid ground, no lava, air above)
4. Teleports player if safe, otherwise tries again
5. Fails after max attempts reached

## Safety Checks
The plugin ensures teleportation safety by:
- Verifying solid block beneath player
- Checking for lava and magma blocks
- Ensuring two air blocks above for player space
- Avoiding water and unsafe terrain
- Using highest block at location

## Building from Source
Requires Java 8+ and Maven 3.6+

```bash
git clone https://github.com/yourusername/SleepRTP.git
cd SleepRTP
mvn clean package
```

Output: `target/SleepRTP-1.0.jar`

## Compatibility
- **Minecraft**: 1.19+
- **Server**: Spigot, Paper, Purpur
- **Java**: 8+

## Examples
```
> /rtp
Finding a random location...
You have been teleported to X: 2847 Y: 64 Z: -1532

> /rtp
Finding a random location...
Couldn't find a safe location. Please try again!
```

## Messages
All messages use Minecraft color codes:
- `§e` Yellow for status messages
- `§a` Green for success
- `§c` Red for errors

## Known Limitations
- No teleport cooldown system
- No warmup timer before teleportation
- No economy/cost integration
- Teleports within current world only
- No biome-specific targeting

## Planned Features
- Cooldown system
- Per-world radius configuration
- Biome selection options
- Teleport warmup timer
- Cost/economy integration
- Multiple home locations support

## License
Open source. Modify and distribute freely.

## Contributing
Contributions welcome via issues and pull requests.

## Support
For issues, verify Minecraft 1.19+ and Java 8+, then check console errors.

---
Simple, reliable random teleportation for Minecraft servers.
