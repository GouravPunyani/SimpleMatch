// @flow
import React, { Component } from 'react'
import { Dimensions, View, StyleSheet, Image } from 'react-native'
import { Text } from '../..'
import { palette, spacing } from '../../theme'
import Swiper from 'react-native-swiper'
import LinearGradient from 'react-native-linear-gradient' // Version 2.0.0
const { width, height } = Dimensions.get('window')

export type Props = {
  children?: React.Children
};

export class DashboardCarousel extends Component<void, Props, void> {
  render () {
    return (
      <View>
        <Swiper style={styles.wrapper}

          onMomentumScrollEnd={(e, state, context) => console.log('index:', state.index)}

          dot={<View style={{backgroundColor: 'rgba(0,0,0,.2)', bottom: height*.2, width: 5, height: 5, borderRadius: 4, marginLeft: 3, marginRight: 3, marginTop: 3, marginBottom: 3}} />}
          activeDot={<View style={{backgroundColor: '#000', bottom: height*.2, width: 8, height: 8, borderRadius: 4, marginLeft: 3, marginRight: 3, marginTop: 3, marginBottom: 3}} />}
          paginationStyle={{
            bottom: 0, left: 0, right: 0
          }} loop>

          <View style={styles.slide} title>
            <Image resizeMode='stretch' style={styles.image} source={require('./one.jpg')} />
            <LinearGradient colors={['rgba(0,0,0,0)', 'rgba(0,0,0,0.3)']} locations={[0,0.4]} style={styles.gradient}/>
            <Text style={styles.text} numberOfLines={2}>THE FUTURE OF FOUNDATION IS HERE. A UNIQUE BLEND FOR EVERY TONE.</Text>
          </View>

          <View style={styles.slide}>
            <Image resizeMode='stretch' style={styles.image} source={require('./two.jpg')} />
            <LinearGradient colors={['rgba(0,0,0,0)', 'rgba(0,0,0,0.3)']} locations={[0,0.4]} style={styles.gradient}/>
            <Text style={styles.text} numberOfLines={2}>WATCH THE QUICK 'HOW TO' VIDEO AND SCAN IN 2 MINUTES TO GET YOURS.</Text>
          </View>

          <View style={styles.slide}>
            <Image resizeMode='stretch' style={styles.image} source={require('./three.jpg')} />
            <LinearGradient colors={['rgba(0,0,0,0)', 'rgba(0,0,0,0.3)']} locations={[0,0.4]} style={styles.gradient}/>
            <Text style={styles.text} numberOfLines={2}>YOUR CUSTOM FOUNDATION IS A GUARANTEED PERFECT MATCH.</Text>
          </View>

          <View style={styles.slide}>
            <Image resizeMode='stretch' style={styles.image} source={require('./four.jpg')} />
            <LinearGradient colors={['rgba(0,0,0,0)', 'rgba(0,0,0,0.3)']} locations={[0,0.4]} style={styles.gradient}/>
            <Text style={styles.text} numberOfLines={2}>INDIVIDUALLY MADE FOR YOUR UNIQUE SKIN TONE.</Text>
          </View>
        </Swiper>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
     paddingBottom: spacing.large
    },
    slide: {
      flex: 1,
      justifyContent: 'center',
      backgroundColor: 'transparent'
    },
    gradient: { 
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: height*.09,
    height: height*.25
    },
    text: {
      position: 'absolute',
      left: width*.05,
      right: width*.05,
      bottom: height*.23,
      paddingTop: spacing.small,
      paddingRight : spacing.small,
      textAlign: 'center',
      justifyContent: 'center',
      fontSize: 16,
      color: 'white',
      fontWeight: 'bold'
    },
    image: {
      width:null,
      height:null,
      resizeMode: 'cover',
      flex: 1
    }
})
