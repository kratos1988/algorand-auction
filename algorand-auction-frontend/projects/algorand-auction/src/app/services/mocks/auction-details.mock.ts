import {AuctionDetails} from '../../models/auction.interface';

export const auction: AuctionDetails = {
  'item': {
    'id': 1,
    'title': 'Mona Lisa',
    'itemName': 'Mona Lisa\'s Painting',
    'description': `Lorem Ipsum is simply dummy text of the printing and typesetting industry. 
        Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, 
        when an unknown printer took a galley of type and scrambled it to make a type specimen book. 
        It has survived not only five centuries, but also the leap into electronic typesetting, 
        remaining essentially unchanged.`,
    'initialValue': 10.99,
    'highestBid': 13.99,
    'expirationDate': '2021-04-12T07:20:00',
    'imageUrl': 'https://www.everypainterpaintshimself.com/images/made/article_images_new/Mona_Lisa_Large_440_666.jpg',
    'userId': 100,
  },
  'bids': [
    {
      'auctionId': 1,
      'amount': 11.99,
      'status': 'INSERTED',
      'userId': '1',
    },
    {
      'auctionId': 1,
      'amount': 13.99,
      'status': 'INSERTED',
      'userId': '2',
    },
  ],
};

