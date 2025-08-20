# Royal Titans Damage Tracker

A RuneLite plugin that tracks your damage contribution during Royal Titans encounters (Branda and Eldric) and calculates your drop rate based on damage dealt.

## Features

- **Real-time Damage Tracking**: Monitors damage dealt to both Branda (Fire Queen) and Eldric (Ice King)
- **Contribution Percentage**: Shows your damage as a percentage of the total boss HP (1200)
- **Dynamic Drop Rate Calculation**: Calculates your drop rate based on damage contribution (base rate: 1/75)
- **Smart Encounter Detection**: Automatically resets when you leave the Royal Titans area or when titans respawn

## Installation

1. Open RuneLite
2. Click the wrench icon to open Configuration
3. Click "Plugin Hub" at the top right of the sidebar
4. Search for "Royal Titans Damage Tracker"
5. Click "Install"

## Usage

The plugin automatically activates when Royal Titans NPCs are detected. The overlay will show:

```
Total: 324 (27.0%)
Branda: 180
Eldric: 144
Drop Rate: 1/278
```

## Configuration Options

Access configuration through RuneLite's Plugin Configuration panel to customize:

- **Display Options**: Toggle individual titan damage and drop rate display
- **Overlay Settings**: Show only during encounters, reset delay timing, and color customization

## How Drop Rate Works

Drop rate for the staff pieces and the prayer scrolls scale with damage contribution:

- **Base Rate**: 1/75 (assuming 100% contribution)
- **Your Rate**: Base rate divided by your contribution percentage
- **Example**: If you deal 25% of total damage, your rate becomes 1/300

## Known Issues

- None

## Changelog

### v1.2.0
- Fixed tracker resetting immediately when titans are killed - now properly waits for configured delay
- Improved encounter detection and area exit handling
- Better state management for titan defeats vs. player leaving area

### v1.1.0
- Attempted fix for reset timing (incomplete)

### v1.0.0
- Initial release

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
