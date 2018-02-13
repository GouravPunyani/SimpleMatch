import Tron from 'reactotron-react-native'

const config = { name: 'MatchCo', safeRecursion: false }
Tron.configure(config).useReactNative()

if (__DEV__) {
  Tron.connect()
  Tron.clear()
}
console.tron = Tron

export const Reactotron = Tron
