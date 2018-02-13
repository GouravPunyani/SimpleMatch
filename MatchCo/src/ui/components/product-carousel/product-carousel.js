// @flow
import React, { Component } from 'react'
import { View, StyleSheet, Dimensions } from 'react-native'
import { BottleImage, Text } from '../..'
import Swiper from 'react-native-swiper'
import { color, palette, spacing } from '../../theme'

export type Props = {
  children?: React.Children,
  sizeMetric: string,
  sizeImperial: string,
  currency: string,
  price: number,
  tint: string,
  name: string,
  date: string
};

export type State = {
  pageIndex: number
};

export class ProductCarousel extends Component<*, Props, State> {
  state = {
    pageIndex: 0
  };

  render () {
    return (
      <View style={styles.wrapper}>
        <Swiper
          height={swiperHeight}
          width={swiperWidth}
          showsPagination
          paginationStyle={styles.pagination}
          showsButtons={false}
          activeDotColor={palette.deepGray}
          dotColor={palette.midGray}
          loop
        >
          <BottleImage
            name={this.props.name}
            face='front'
            tint={this.props.tint}
          />
          <BottleImage
            name={this.props.name}
            date={this.props.date}
            face='back'
            tint={this.props.tint}
          />
        </Swiper>
        <View style={styles.footer}>
          <View style={styles.size}>
            <Text
              small
              fancy
              lighter
              text={this.props.sizeMetric}
              style={styles.metric}
            />
            <View style={styles.line} />
            <Text
              small
              fancy
              lighter
              text={this.props.sizeImperial}
              style={styles.imperial}
            />
          </View>
          <View style={styles.money}>
            <Text small fancy lighter text={this.props.currency} />
            <Text title fancy lighter text={this.props.price} />
          </View>
        </View>
      </View>
    )
  }
}

const height = 300
const swiperHeight = height + spacing.large + spacing.medium
const swiperWidth = Dimensions.get('window').width - spacing.small * 2

const styles = StyleSheet.create({
  wrapper: { height: swiperHeight },
  greyText: { color: color.textLight },
  pagination: { bottom: 10 },
  footer: {
    flexDirection: 'row',
    alignSelf: 'center',
    backgroundColor: 'transparent',
    alignItems: 'center',
    position: 'absolute',
    bottom: 0
  },
  line: {
    marginTop: 2,
    marginBottom: 1,
    height: 1,
    backgroundColor: color.textLighter
  },
  size: {
    marginRight: spacing.extraLarge
  },
  money: {
    marginLeft: spacing.extraLarge,
    flexDirection: 'row',
    alignItems: 'center'
  }
})
