use strict;
use warnings;
use utf8;

use LWP::Authen::OAuth2;
use URI::Escape;
use LWP::Simple;
use Mail::IMAPClient;
use JSON qw( decode_json );
use Data::Dumper;
use MIME::Base64;


sub getNewAccessToken
{
        ## Fetch WNS client ID, secret, and URI from persistent store 
        ## (or you can hard code them/put in config file, whatever)
        ## and put them into client_id, client_secret URL encoded
        my $server_endpoint = "https://login.microsoftonline.com/12059d27-cbf5-4286-94d6-5883a2bbde8c/oauth2/v2.0/token";

        my $client_id      = "2136249c-64c4-4755-adc4-6049e596b728";
        my $client_secret  = "4tM8Q~gJSPGVKCoZMNWBrxHFYXFfjR5~mVauAbDb";

        my $payload = "grant_type=client_credentials&client_id=$client_id&client_secret=$client_secret&scope=https://outlook.office365.com/.default";

	    my $ua  = LWP::UserAgent->new;        
        my $req = HTTP::Request->new(POST => $server_endpoint);
           $req->content($payload);
		my $resp = $ua->request($req);
							
        my $decoded_json = decode_json( $resp->content );
        my $access_token = $decoded_json->{'access_token'};
		
		#print $access_token;

        if ($resp->is_success) {
                ## Update the access token in the database/persistent store
				print "success";
        } else {
                ## Yeeps! Something went wrong.  Send an alert, log, etc here. 
				print "failure";
        }
		
		return $access_token;
}
                ## If at first you don't succeed,
                ## get a new access token and try again.
                my $access_token = getNewAccessToken();
				
				
my $username = "edi_dropbox\@windows.franklinamerican.com"; # Login ID of the user same as an above token.

my $oauth_sign = encode_base64("user=". $username ."\x01auth=Bearer ". $access_token ."\x01\x01", '');
print $access_token;

my $imapC = Mail::IMAPClient->new(
	Server	=>	'outlook.office365.com',
	Port	=>	993,
	Ssl		=> 1,
	Uid		=> 1,
) or die('Can\'t connect to imap server.');
$imapC->authenticate('XOAUTH2', sub { return $oauth_sign }) or die("Auth error: ". $imapC->LastError);

print $imapC->folders or die("List folders error: ". $imapC->LastError);
     

;
