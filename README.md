# HaidarVM

Android-first general-purpose virtual machine manager for high-end devices.

## Goals

- No-root-first architecture where Android permits it
- Windows x86_64 guest support for gaming
- Linux x86_64 and ARM64 guests
- Optional macOS support subject to platform and licensing constraints
- Real hardware-accelerated graphics only when the guest/host driver path supports it
- Background OS/ISO downloads with resumable progress
- VM import for ISO, IMG, QCOW2 and other supported disk formats
- Gamepad, keyboard and mouse input
- Clean, gaming-focused VM management UI

## Initial milestone

1. Android app skeleton
2. VM engine abstraction
3. Hardware capability detection
4. First QEMU-based prototype
5. x86_64 guest boot test
6. GPU acceleration capability test
7. GitHub Actions APK build

GPU acceleration is never reported as enabled unless an actual accelerated rendering path is detected.
