# Royal Titans Damage Tracker

A RuneLite plugin that tracks your damage contribution during Royal Titans encounters (Branda and Eldric) and calculates your drop rate based on damage dealt.

## Features

- **Real-time Damage Tracking**: Monitors damage dealt to both Branda (Fire Queen) and Eldric (Ice King)
- **Contribution Percentage**: Shows your damage as a percentage of the total boss HP (1200)
- **Dynamic Drop Rate Calculation**: Calculates your drop rate based on damage contribution (base rate: 1/75)
- **Smart Area Detection**: Automatically resets when you leave the Royal Titans area
- **Encounter Management**: Handles titan respawns and encounter resets intelligently
- **Customizable Display**: Configure colors, visibility, and information shown

## Installation

1. Download the latest release from the [Releases](https://github.com/Mojac/royal-titans-damage-tracker-plugin/releases) page
2. Place the `.jar` file in your RuneLite plugins folder:
   - **Windows**: `%USERPROFILE%\.runelite\plugins`
   - **macOS**: `~/.runelite/plugins`
   - **Linux**: `~/.runelite/plugins`
3. Restart RuneLite
4. Enable the plugin in the Plugin Hub or Plugin Configuration

## Usage

The plugin automatically activates when Royal Titans NPCs are detected. The overlay will show:

- **Total Damage**: Combined damage to both titans with contribution percentage
- **Individual Damage**: Separate damage counters for Branda and Eldric (configurable)
- **Drop Rate**: Calculated drop rate based on your damage contribution (configurable)

### Example Display
```
Total: 324 (27.0%)
Branda: 180
Eldric: 144
Drop Rate: 1/278
```

## Configuration Options

Access configuration through RuneLite's Plugin Configuration panel:

### Display Options
- **Show Individual Titan Damage**: Toggle individual Branda/Eldric damage lines
- **Show Drop Rate**: Toggle drop rate calculation display

### Overlay Settings
- **Show Only During Encounter**: Hide overlay when not fighting Royal Titans
- **Reset Delay**: Delay (0-18 seconds) before resetting counters after encounter ends
- **Color Customization**: Set custom colors for different text elements

## How Drop Rate Works

The plugin calculates drop rates based on the Royal Titans mechanic where drop rate scales with damage contribution:

- **Base Rate**: 1/75 (assuming 100% damage contribution)
- **Your Rate**: Base rate divided by your contribution percentage
- **Example**: If you deal 25% of total damage, your rate becomes 1/300

## Development

### Building from Source

```bash
git clone https://github.com/Mojac/royal-titans-damage-tracker-plugin.git
cd royal-titans-damage-tracker-plugin
./gradlew build
```

### Project Structure
```
src/main/java/com/royaltitans/
├── RoyalTitansPlugin.java      # Main plugin logic
├── RoyalTitansConfig.java      # Configuration interface  
└── RoyalTitansOverlay.java     # UI overlay rendering
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Known Issues

- None

## Changelog

### v1.0.0
- Initial release
- Basic damage tracking for Royal Titans
- Drop rate calculation
- Configurable overlay with color options
- Smart encounter detection and area exit handling

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built for the RuneLite client
- Thanks to the RuneLite community for API documentation
- Claude Sonnet 4 and Copilot for vibe coding

## Support

If you encounter issues or have suggestions:
- Open an [Issue](https://github.com/Mojac/royal-titans-damage-tracker-plugin/issues)
- Check existing issues for solutions
- Provide detailed information about your RuneLite version and the problem

---

**Note**: This plugin is not affiliated with Jagex or Old School RuneScape. Use at your own discretion.
