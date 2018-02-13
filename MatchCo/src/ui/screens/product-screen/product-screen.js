// @flow

import React, { Component } from 'react'
import { Platform, StyleSheet } from 'react-native'
import {
  ScrollingLayout,
  Text,
  ProductCarousel,
  Button,
  ProductTabBar,
  TestimonialList
} from '../..'
import { inject, observer } from 'mobx-react'
import { translator } from '../../../services/translate/translator'
import { ProductsStore } from '../../../stores/products-store'
import { UserStore } from '../../../stores/user-store'
import { palette, spacing } from '../../theme'
import { bind } from 'decko'

export type Props = {
  productsStore: ProductsStore,
  userStore: UserStore,
  productId: string,
  translate: (key: string) => string
};

export type State = {
  carouselIndex: number,
  tabIndex: number
};

@translator
@inject('productsStore', 'userStore')
@observer
export class ProductScreen extends Component<*, Props, State> {
  state = {
    carouselIndex: 0,
    tabIndex: 0
  }

  @bind
  onTabChange (newIndex: number) {
    this.setState({ tabIndex: newIndex })
  }

  @bind
  onCarouselChange (newIndex: number) {
    this.setState({ carouselIndex: newIndex })
  }

  @bind
  onApplePay () {
    // TODO(steve): find a react-native component that wraps ApplePay
  }

  render () {
    const { translate, productsStore, userStore, productId = '1' } = this.props
    const product = productsStore.products[productId]
    const showApplePay = Platform.OS === 'ios'
    const currency = '$' // TODO(steve): does i18n support $ and numbers too?
    return (
      <ScrollingLayout containerContentStyle={styles.wrapper}>
        <ProductCarousel
          onChange={this.onCarouselChange}
          sizeMetric={product.sizeMetric}
          sizeImperial={product.sizeImperial}
          currency={currency}
          price={product.price}
          tint={userStore.user.scanTint}
          name={userStore.user.displayName}
          date={userStore.user.scanDate}
        />
        <Button
          text={translate('product.checkoutButton')}
          style={styles.button}
        />
        {showApplePay &&
          <Button
            text={translate('product.applePay')}
            style={styles.appleButton}
          />
        }
        <Text
          text={translate('contactScreen.returnPolicy')}
          style={styles.policy}
          small
          fancy
        />
        <ProductTabBar onChange={this.onTabChange} />
        {this.state.tabIndex === 0 &&
          <Text
            text={product.description}
            style={styles.longText}
          />}
        {this.state.tabIndex === 1 &&
          <Text
            text={product.ingredients}
            style={styles.longText}
          />}
        {this.state.tabIndex === 2 &&
          <TestimonialList testimonials={product.testimonials} />}

      </ScrollingLayout>
    )
  }
}

const styles = {
  wrapper: { paddingBottom: spacing.large },
  policy: { paddingVertical: spacing.medium },
  longText: { paddingTop: spacing.medium },
  button: { marginTop: spacing.medium },
  appleButton: { marginTop: spacing.medium, backgroundColor: palette.nearBlack, marginBottom: spacing.small }
}
