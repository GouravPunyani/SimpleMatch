// @flow

import { observable, toJS } from 'mobx'
import { Product } from '../entities/product'
import { Testimonial } from '../entities/testimonial'

const tempProduct = new Product()
tempProduct.id = '1'
tempProduct.name = 'Your Custom Blend'
tempProduct.description =
  'Description ... Lorem ipsum dolor sit amet, consectetur adipisicing elit. Esse debitis reprehenderit provident, voluptatibus. Sit a, non, tempora nostrum et reprehenderit ad sapiente, natus asperiores nihil perferendis eum, libero cum! Porro!'
tempProduct.ingredients =
  'Ingredientes ... Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ratione molestias aperiam optio tempore voluptatem ipsum facilis magni fugiat autem ipsa, quas fuga possimus corporis eum similique. Obcaecati aspernatur at modi! '
tempProduct.price = 49.0
tempProduct.sizeImperial = '1 fl oz'
tempProduct.sizeMetric = '30 ml'

const t1 = new Testimonial()
t1.id = 'dominique'
t1.name = 'Dominique'
t1.quote =
  "This is just so easy and straightforward and you know you're getting something that's just for you."
t1.image = require('../ui/components/testimonial/dominique.png')
t1.videoUrl = 'https://vimeo.com/134650399'
const t2 = new Testimonial()
t2.id = 'yvette'
t2.name = 'Yvette'
t2.quote =
  "I love that it's 3 products in 1 and the fact that it makes my skin glow by you don't see that I'm wearing any makeup."
t2.image = require('../ui/components/testimonial/yvette.png')
t2.videoUrl = 'https://vimeo.com/143175989'

const t3 = new Testimonial()
t3.id = 'rachel'
t3.name = 'Rachel'
t3.quote =
  "It's a no brainer, you either go to a department store and get sold a ton of stuff or just buy from your App from home and it's custom made just for you."
t3.image = require('../ui/components/testimonial/rachel.png')
t3.videoUrl = 'https://vimeo.com/134648847'

const t4 = new Testimonial()
t4.id = 'kara'
t4.name = 'Kara'
t4.quote =
  "This is brilliant; if I can know that I'm going to get the best color match and that it's customized for me by using an App for a few minutes why wouldn't I do that?"
t4.image = require('../ui/components/testimonial/kara.png')
t4.videoUrl = 'https://vimeo.com/134645249'

tempProduct.testimonials = [t1, t2, t3, t4]

export class ProductsStore {
  @observable products: {[string]: Product} = {'1': tempProduct}

  constructor (state?: *) {
    if (state) {
      // TODO(steve): should this be rehydratable? hmm...
      // this.products = []
    }
  }

  serialize (): Object {
    return toJS(this.products)
  }
}
