# Royal Titans Damage Tracker

A RuneLite plugin that tracks your damage contribution during Royal Titans encounters (Branda and Eldric) and calculates your drop rate based on damage dealt.

## Features

- **Real-time Damage Tracking**: Monitors damage dealt to both Branda (Fire Queen) and Eldric (Ice King)
- **Contribution Percentage**: Shows your damage as a percentage of the total boss HP (1200)
- **Dynamic Drop Rate Calculation**: Calculates your drop rate based on damage contribution (base rate: 1/75)
- **Smart Encounter Detection**: Automatically resets when you leave the Royal Titans area or when titans respawn
- **Burn Damage Support**: Optionally tracks and includes player applied burn damage in calculations

## Installation

1. Open RuneLite
2. Click the wrench icon to open Configuration
3. Click "Plugin Hub" at the top right of the sidebar
4. Search for "Royal Titans Damage Tracker"
5. Click "Install"

## Usage

The plugin automatically activates when Royal Titans NPCs are detected. The overlay will show:

```
Total: 486 (40.5%)
Branda: 180
Eldric: 144
Branda Burn: 95
Eldric Burn: 67
Drop Rate: 1/185 (w/ burn)
```

## Configuration Options

Access configuration through RuneLite's Plugin Configuration panel to customize:

- **Display Options**: Toggle individual titan damage, burn damage and drop rate display lines
- **Damage Calculation**: Include burn damage in total damage contribution and drop rate calculation
- **Overlay Settings**: Show only during encounters, reset delay timing, and color customization

## How Drop Rate Works

Drop rate for the staff pieces and the prayer scrolls scale with damage contribution:

- **Base Rate**: 1/75 (assuming 100% contribution)
- **Your Rate**: Base rate divided by your contribution percentage
- **Example**: If you deal 25% of total damage, your rate becomes 1/300

## Burn Damage Mechanics

- Few weapons in OSRS apply the burn effect to monsters, namely: the Atlatl (w/ Eclipse moon armour set), Arkan blade and Burning claws.
- Burn is not attributed to any player (isMine() and isOther() are both false)
- When "Include Burn Damage" is enabled, it's added to your total contribution
- The overlay shows whether burn damage is included in drop rate calculations

### When to Include Burn Damage

- **Solo encounters**: All burn damage effectively benefits you, so including it gives a more accurate drop rate
- **Duo encounters**: Only include if you're confident your partner isn't also burning the titans

## Known Issues

- "Include Burn Damage" being active while both players in a duo encounter use weapons that apply burn causes the damage tracker to track both players' burn effects

## Changelog

### v1.3.0
- Added burn damage tracking and configuration options
- Added "Include Burn Damage" toggle for drop rate calculations
- Added "Display Burn Damage" toggle for overlay visibility
- Enhanced overlay to show burn damage inclusion status in drop rate
- Improved damage calculation flexibility for solo vs. group scenarios
- Added burn damage color customization

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
